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
            JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
            return json;
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

    @Override
    public String getPictureUrl(JsonObject memberInfo) {
        return memberInfo.getAsJsonObject("properties").get("profile_image").getAsString();
    }

    @Override
    public String getGender(JsonObject memberInfo) {
        if (memberInfo.getAsJsonObject(KAKAO_ACCOUNT.getValue()).get("has_gender").getAsBoolean() &&
                !memberInfo.getAsJsonObject(KAKAO_ACCOUNT.getValue()).get("gender_needs_agreement").getAsBoolean()) {
            return memberInfo.getAsJsonObject(KAKAO_ACCOUNT.getValue()).get("gender").getAsString();
        }
        return "동의안함";
    }

    @Override
    public String getAgeRange(JsonObject memberInfo) {
        String KAKAO_ACCOUNT = "kakao_account";
        if (memberInfo.getAsJsonObject(KAKAO_ACCOUNT).get("has_age_range").getAsBoolean() &&
                !memberInfo.getAsJsonObject(KAKAO_ACCOUNT).get("age_range_needs_agreement").getAsBoolean()) {
            return memberInfo.getAsJsonObject(KAKAO_ACCOUNT).get("age_range").getAsString();
        }
        return "동의안함";
    }
}