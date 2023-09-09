package com.sidenow.global.config.redis.dto;

import com.sidenow.global.config.jwt.TokenInfoResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public abstract class RefreshTokenDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "토큰 재발급을 위한 요청 객체")
    public static class RefreshTokenRequest {

        @NotBlank(message = "리프레시 토큰을 입력해주세요.")
        @Schema(description = "리프레시 토큰을 주세요.")
        private String refreshToken;

        @NotNull(message = "유저 id를 입력해 주세요.")
        @Schema(description = "유저 id를 입력해 주세요.")
        private Long memberId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "토큰 재발급을 위한 응답 객체")
    public static class RefreshTokenResponse {
        private String accessToken;
        private String refreshToken;

        public static RefreshTokenResponse from(TokenInfoResponse tokenInfoResponse) {
            return RefreshTokenResponse.builder()
                    .accessToken(tokenInfoResponse.getAccessToken())
                    .refreshToken(tokenInfoResponse.getRefreshToken())
                    .build();
        }
    }
}
