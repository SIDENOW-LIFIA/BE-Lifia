package com.sidenow.global.config.jwt;

import com.sidenow.global.config.jwt.constant.JwtContants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 인증되지 않은 사용자가 보호된 리소스에 액세스할 때 호출
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = String.valueOf(request.getAttribute("exception")); // String.valueOf(): Object값을 String으로 변환 (toString()과 동일하나 null체크 방법 상이)

        if (exception.equals(JwtContants.JWTExceptionList.ADDITIONAL_REQUIRED_TOKEN.getErrorCode())) {

        }
    }

    private void setResponse(HttpServletResponse response, JwtContants.JWTExceptionList exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("timestamp", LocalDateTime.now().withNano(0).toString());
        responseJson.put("message", exceptionCode.getMessage());
        responseJson.put("errorCode", exceptionCode.getErrorCode());

        response.getWriter().print(responseJson);
    }
}
