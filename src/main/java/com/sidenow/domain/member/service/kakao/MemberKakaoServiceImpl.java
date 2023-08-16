package com.sidenow.domain.member.service.kakao;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sidenow.domain.member.exception.ConnException;
import com.sidenow.domain.member.exception.NotFoundEmailException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.sidenow.domain.member.constant.MemberConstant.MemberServiceMessage.KAKAO_ACCOUNT;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberKakaoServiceImpl implements MemberKakaoService{
    @Override
    public JsonObject connectKakao(String reqURL, String token) {
        try{
            URL url = new URL(reqURL);
            System.out.println(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); // 전송할 Header 작성, access_token 전송

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            in.close();
            return JsonParser.parseString(response.toString()).getAsJsonObject();
        } catch (IOException e){
            throw new ConnException();
        }
    }

    @Override
    public String getEmail(JsonObject memberInfo) {
        if (memberInfo.getAsJsonObject(KAKAO_ACCOUNT.getValue()).get("has_email").getAsBoolean()){
            return memberInfo.getAsJsonObject(KAKAO_ACCOUNT.getValue()).get("email").getAsString();
        }
        throw new NotFoundEmailException();
    }
}