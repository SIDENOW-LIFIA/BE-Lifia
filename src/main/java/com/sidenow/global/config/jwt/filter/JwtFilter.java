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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
// 역할: HttpServletRequest 헤더(Authorization)에 들어있는 Access Token을 꺼내 여러가지 검사 후 유저정보 추출하여 SecurityContextHolder에 저장
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;
    private final String NO_CHECK_URL = "/login";

    // 실제 필터링 로직을 수행
    // 가입/로그인/재발급을 제외한 모든 Request 요청은 이 필터를 거치므로, 토큰 정보가 없거나 유효하지 않으면 정상적으로 수행되지 않음.
    // 요청이 정상적으로 Controller까지 도착했다면, SecurityContext에 MemberId가 존재한다는 것이 보장됨.
    // 인증 실패 시 아무런 과정 없이 다음 필터로 넘어감

    /**
     * 1. RefreshToken이 오는 경우 -> 유효하면 AccessToken 재발급 후 필터 진행 X, 바로 튕겨버리기
     * 2. RefreshToken이 없고, AccessToken만 오는 경우 -> 유저 정보 저장 후 필터 계속 진행
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, JwtException {

        log.info("JWT Filter 진입");
        if (request.getRequestURI().equals(NO_CHECK_URL)){

            filterChain.doFilter(request, response);
            return;
        }



        /*log.info("JWT Filter 진입");
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        if (!requestURI.contains("/api/auth/logout")){
            try {
                // JWT 유무, 유효 여부, 블랙리스트에 등록 여부 확인 후 정상 토큰이면 SecurityContext에 저장
                if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) && tokenProvider.checkBlackList(jwt)) {
                    if (requestURI.contains("/member/re-issue")) {
                        log.info("Token 재발급 진행 시 유효성 검사");
                        checkRefreshTokenAndReIssueAccessToken(jwt);
                    } else {
                        tokenProvider.checkMultiLogin(jwt);
                    }

                    Authentication authentication = tokenProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
                }
            } catch (RemovedAccessTokenException e) {
                log.warn("RemovedAccessToken reject");
                log.error("exception : {}", e.getMessage());
                throw e;
            } catch (SecurityException | MalformedJwtException e) {
                log.error("exception : {}", e.getMessage());
                throw new MalformedException();
            } catch (ExpiredJwtException e) {
                log.error("exception : {}", e.getMessage());
                throw new ExpiredException();
            } catch (UnsupportedJwtException e) {
                log.error("exception : {}", e.getMessage());
                throw new UnsupportedException();
            } catch (IllegalArgumentException e) {
                log.error("exception : {}", e.getMessage());
                throw new IllegalException();
            } catch (Exception e) {
                log.error("exception : {}", e.getMessage());
                throw new UnknownException();
            }
        }
        filterChain.doFilter(request, response);*/
    }

    // Http Request로부터 토큰을 가져오는 메서드
    private String resolveToken(HttpServletRequest request) {
        log.info("Resolve Token 진입");
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.info("Claim 추출");
            return bearerToken.substring(7); // "Bearer " 7글자 빼고 나머지 부분(Access Token) 추출 / 앞부분은 type임. 요청헤더의 구성은 Authorization: <type> <credentials>임
        }
        log.info("토큰 없음");
        return null;
    }

    private void checkRefreshTokenAndReIssueAccessToken(String token) {

        log.info("Refresh Token 검증 진입");
        try {
            tokenProvider.validateRefreshToken(token);
        } catch (Exception e) {
            log.error("예외 발생 {}", e.getMessage());
            throw e;
        }
    }

}
