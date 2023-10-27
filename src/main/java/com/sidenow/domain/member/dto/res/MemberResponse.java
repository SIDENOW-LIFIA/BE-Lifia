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
        private final String nickname;

        public static MemberLoginResponse from(TokenResponse tokenResponse, Member member) {
            return MemberLoginResponse.builder()
                    .accessToken(tokenResponse.getAccessToken())
                    .refreshToken(tokenResponse.getRefreshToken())
                    .nickname(member.getNickname())
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

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class MemberSimpleResponse {
        private final String name;
        private final String nickname;

        public static MemberSimpleResponse from(Member member){

            return MemberSimpleResponse.builder()
                    .name(member.getName())
                    .nickname(member.getNickname())
                    .build();
        }
    }

}
