package com.sidenow.global.config.jwt;

import com.sidenow.global.config.jwt.constant.JwtContants;
import com.sidenow.global.config.jwt.exception.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, JwtException {
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        if (!requestURI.contains("/api/auth/logout")){
            try {
                if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) && tokenProvider.checkBlackList(jwt)) {
                    if (requestURI.contains("/user/re-issue")) {
                        log.info("Token 재발급 진행 시 유효성 검사");
                        checkRefreshTokenAndReIssueAccessToken(jwt);
                    } else{
                        tokenProvider.
                    }
                }
            }
        }
    }

    // Http Request로부터 토큰을 가져오는 메서드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 7글자 빼고 나머지 부분(Access Token) 추출 / 앞부분은 type임. 요청헤더의 구성은 Authorization: <type> <credentials>임
        }
        return null;
    }

    private void checkRefreshTokenAndReIssueAccessToken(String token) {

        try {
            tokenProvider.validateRefreshToken(token);
        } catch (Exception e) {
            log.error("예외 발생 {}", e.getMessage());
            throw e;
        }
    }

}
