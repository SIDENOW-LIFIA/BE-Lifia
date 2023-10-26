package com.sidenow.global.config.jwt;

import lombok.RequiredArgsConstructor;

public class JwtPrincipal {
    public final String accessToken;
    public final String nickname;
    public final String email;
    public final Long memberId;
    public final Long apartmentId;

    JwtPrincipal(String accessToken, String nickname,
                 String email, Long memberId, Long apartmentId){
        this.accessToken = accessToken;
        this.nickname = nickname;
        this.email = email;
        this.memberId = memberId;
        this.apartmentId = apartmentId;
    }
}
