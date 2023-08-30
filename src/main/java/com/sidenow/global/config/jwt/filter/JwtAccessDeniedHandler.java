package com.sidenow.global.config.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sidenow.global.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;


@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 권한이 없는 사용자가 보호된 리소스에 액세스 할 때 호출
     */

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // 필요한 권한이 없이 접근하려 할 때 403 에러
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}