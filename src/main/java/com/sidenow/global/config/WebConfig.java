package com.sidenow.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Cors를 적용할 URL 패턴 정의
                .allowedOrigins("*") // 자원 공유를 허락할 Origin 지정 (모든 Origin 허락)
                .allowedMethods(ALLOWED_METHOD_NAMES.split(",")) // 허용할 HTTP method 지정
                .exposedHeaders(HttpHeaders.LOCATION) // 클라이언트 측 응답에서 노출되는 헤더 지정
                .maxAge(5000); // 원하는 시간만큼 Preflight Request를 캐싱
    }

}
