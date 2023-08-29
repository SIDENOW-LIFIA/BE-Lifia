package com.sidenow.global.config.jwt;

import com.sidenow.global.config.jwt.constant.JwtContants;
import com.sidenow.global.config.jwt.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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
                // JWT 유무, 유효 여부, 블랙리스트에 등록 여부 확인
                if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) && tokenProvider.checkBlackList(jwt)) {
                    if (requestURI.contains("/user/re-issue")) {
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
        filterChain.doFilter(request, response);
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
