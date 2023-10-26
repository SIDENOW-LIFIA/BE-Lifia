package com.sidenow.domain.member.dto.res;

import com.sidenow.domain.member.entity.Member;
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

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "마이페이지 회원 정보 응답 객체")
    public static class MemberInfoResponse {
        private final String email;
        private final String name;
        private final String nickname;
        private final String apartment;

        public static MemberInfoResponse from(Member member){

            return MemberInfoResponse.builder()
                    .email(member.getEmail())
                    .name(member.getName())
                    .nickname(member.getNickname())
                    .apartment(member.getApartment())
                    .build();
        }
    }

}
