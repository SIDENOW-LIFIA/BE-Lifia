//package com.sidenow.global.config.jwt;
//
//import com.sidenow.global.config.jwt.constant.JwtContants;
//import com.sidenow.global.config.jwt.exception.*;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Slf4j
//@RequiredArgsConstructor
//public class JwtFilter extends OncePerRequestFilter {
//
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//    private final TokenProvider tokenProvider;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, JwtException {
//        String jwt = resolveToken(request);
//        String requestURI = request.getRequestURI();
//        try {
//            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//                boolean isAdditionalInfoProvided = tokenProvider.getAdditionalInfoProvided(jwt);
//                if (isAdditionalInfoProvided) {
//                    Authentication authentication = tokenProvider.getAuthentication(jwt);
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    log.debug("Security Context '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
//                } else {
//                    throw new AdditionalInfoException();
//                }
//            }
//        } catch (AdditionalInfoException e) {
//            request.setAttribute("exception", JwtContants.JWTExceptionList.ADDITIONAL_REQUIRED_TOKEN.getErrorCode());
//        } catch (SecurityException | MalformedException e) {
//            request.setAttribute("exception", JwtContants.JWTExceptionList.MAL_FORMED_TOKEN.getErrorCode());
//        } catch (ExpiredException e) {
//            request.setAttribute("exception", JwtContants.JWTExceptionList.EXPIRED_TOKEN.getErrorCode());
//        } catch (UnsupportedException e) {
//            request.setAttribute("exception", JwtContants.JWTExceptionList.UNSUPPORTED_TOKEN.getErrorCode());
//        } catch (IllegalException e) {
//            request.setAttribute("exception", JwtContants.JWTExceptionList.ILLEGAL_TOKEN.getErrorCode());
//        } catch (Exception e) {
//            log.error("================================================");
//            log.error("JwtFilter - doFilterInternal() 오류발생");
//            log.error("token : {}", jwt);
//            log.error("Exception Message : {}", e.getMessage());
//            log.error("Exception StackTrace : {");
//            e.printStackTrace();
//            log.error("}");
//            log.error("================================================");
//            request.setAttribute("exception", JwtContants.JWTExceptionList.UNKNOWN_ERROR.getErrorCode());
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    // Http Request로부터 토큰을 가져오는 메서드
//    private String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7); // "Bearer " 7글자 빼고 나머지 부분(Access Token) 추출 / 앞부분은 type임. 요청헤더의 구성은 Authorization: <type> <credentials>임
//        }
//        return null;
//    }
//
//}
