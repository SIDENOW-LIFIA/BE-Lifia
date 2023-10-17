package com.sidenow.global.config.jwt.filter;

import com.sidenow.global.config.jwt.TokenProvider;
import com.sidenow.global.config.jwt.constant.JwtContants;
import com.sidenow.global.config.jwt.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.lettuce.core.dynamic.annotation.Value;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import reactor.util.annotation.NonNullApi;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
// 역할: HttpServletRequest 헤더(Authorization)에 들어있는 Access Token을 꺼내 여러가지 검사 후 유저정보 추출하여 SecurityContextHolder에 저장
// 클라이언트 요청 시 JWT 인증을 하기 위해 설치하는 Custom Filter로, UsernamePasswordAuthenticationFilter 이전에 실행
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final TokenProvider tokenProvider;

    // 실제 필터링 로직을 수행
    // 가입/로그인/재발급을 제외한 모든 Request 요청은 이 필터를 거치므로, 토큰 정보가 없거나 유효하지 않으면 정상적으로 수행되지 않음.
    // 토큰의 인증 정보를 현재 실행 중인 SecurityContext에 저장
    // 요청이 정상적으로 Controller까지 도착했다면, SecurityContext에 MemberId가 존재한다는 것이 보장됨.
    // 인증 실패 시 아무런 과정 없이 다음 필터로 넘어감
    /**
     * 1. RefreshToken이 오는 경우 -> 유효하면 AccessToken 재발급 후 필터 진행 X, 바로 튕겨버리기
     * 2. RefreshToken이 없고, AccessToken만 오는 경우 -> 유저 정보 저장 후 필터 계속 진행
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, JwtException {
        log.info("JWT Filter 진입");

        // resolveToken을 통해 토큰을 받아와서 유효성 검증을 하고 정상 토큰이면 SecurityContext에 저장
        String token = tokenProvider.resolveToken(request);
        log.info("Token: "+token);

        String requestURI = request.getRequestURI();

        if (token != null && tokenProvider.validateToken(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        }
        filterChain.doFilter(request, response);
    }
}
