package com.sidenow.global.config.aws.dto;

import com.amazonaws.services.simpleemail.model.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    public static final String FROM_EMAIL = "kusitms.hjs@gmail.com"; // 보내는 사람

    private String receiver; // 받는 사람
    private String subject; // 제목
    private String content; // 본문

    public SendEmailRequest toSendRequestDto() {

        // 목적지 설정
        Destination destination = new Destination()
                .withToAddresses(receiver);

        // 제목, 본문 설정
        Message message = new Message().withSubject(createContent(subject))
                .withBody(new Body().withHtml(createContent(content)));

        return new SendEmailRequest().withSource(FROM_EMAIL).withDestination(destination)
                .withMessage(message);
    }

    // 본문 형식 설정
    private Content createContent(String text) {
        return new Content().withCharset("UTF-8").withData(text);
    }
}
