package com.sidenow.domain.member.dto.res;

import com.sidenow.global.config.jwt.TokenResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


public abstract class MemberResponse {

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "로그인 응답 객체")
    public static class MemberLoginResponse{
        private final String accessToken;
        private final String refreshToken;

        public static MemberLoginResponse from(TokenResponse tokenResponse) {
            return MemberLoginResponse.builder()
                    .accessToken(tokenResponse.getAccessToken())
                    .refreshToken(tokenResponse.getRefreshToken())
                    .build();
        }
    }

}
