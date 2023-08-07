package com.sidenow.global.config.redis.controller;

import com.sidenow.global.config.redis.dto.RefreshTokenDto;
import com.sidenow.global.config.redis.service.RefreshTokenService;
import com.sidenow.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/members")
@Tag(name = "토큰 재발급 API", description = "토큰을 API")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "토큰 재발급", description = "토큰을 재발급합니다.")
    @PostMapping("/auth/refresh")
    public ResponseEntity<ResponseDto<RefreshTokenDto.RefreshTokenResponse>> tokenRefresh(@Valid @RequestBody RefreshTokenDto.)

}
