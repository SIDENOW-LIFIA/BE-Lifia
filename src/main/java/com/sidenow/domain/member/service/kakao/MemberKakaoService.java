package com.sidenow.domain.member.service.kakao;

import com.google.gson.JsonObject;

public interface MemberKakaoService {
    JsonObject connectKakao(String reqURL, String token);
    String getEmail(JsonObject memberInfo);
    String getPictureUrl(JsonObject memberInfo);
    String getGender(JsonObject memberInfo);
    String getAgeRange(JsonObject memberInfo);
}
