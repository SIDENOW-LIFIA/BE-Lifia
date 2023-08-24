package com.sidenow.domain.member.service;

import com.google.gson.JsonObject;

public interface MemberKakaoService {
    JsonObject connectKakao(String reqURL, String token);
    String getEmail(JsonObject memberInfo);
}
