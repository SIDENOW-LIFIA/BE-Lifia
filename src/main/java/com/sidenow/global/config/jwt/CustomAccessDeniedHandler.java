package com.sidenow.global.config.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidenow.global.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 권한이 없는 사용자가 보호된 리소스에 액세스 할 때 호출
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse("403", "접근이 금지되었습니다. 권한이 없는 사용자가 접근하려고 했습니다.");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper(); // Java 객체를 JSON으로 직렬화 (변환)
        mapper.writeValue(response.getWriter(), errorResponse);
    }
}