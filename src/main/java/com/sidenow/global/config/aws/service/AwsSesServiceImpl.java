package com.sidenow.global.config.aws.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.sidenow.global.config.aws.dto.AwsEmailDto;
import com.sidenow.global.config.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsSesServiceImpl implements AwsSesService{

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final RedisUtil redisUtil;
    private final String content = createContent();
    private final String code = createCode();

    // 이메일 인증 코드 전송
    @Override
    public String send(String receiver){
        String subject = "LIFIA 회원가입 인증번호";
        AwsEmailDto emailDto = AwsEmailDto.builder()
                .receiver(receiver)
                .subject(subject)
                .content(content)
                .build();

        if (redisUtil.existData(receiver)) {
            redisUtil.deleteData(receiver);
        }
        SendEmailResult sendEmailResult = amazonSimpleEmailService.sendEmail(emailDto.toSendRequestDto());
        redisUtil.setDataExpire(receiver, code, 60 * 5L);
        return sendEmailResult.getSdkResponseMetadata().toString();
    }

    // 이메일 인증 코드 검증
    @Override
    public boolean verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);
        if (codeFoundByEmail == null) {
            return false;
        }
        return redisUtil.getData(email).equals(code);
    }

    // 이메일 내용 생성
    private String createContent() {
        String content="";
        content+= "<div style='margin:20px;'>";
        content+= "<h1> LIFIA </h1>";
        content+= "<br>";
        content+= "<p>아래 코드를 복사해 입력해주세요<p>";
        content+= "<br>";
        content+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        content+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        content+= "<div style='font-size:130%'>";
        content+= "CODE : <strong>";
        content+= code +"</strong><div><br/> ";
        content+= "</div>";
        return content;
    }

    // 이메일 인증코드 생성
    private String createCode() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }
}
