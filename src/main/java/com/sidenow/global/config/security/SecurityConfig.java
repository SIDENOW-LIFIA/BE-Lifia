package com.sidenow.global.config.security;

import com.sidenow.global.config.jwt.filter.JwtAccessDeniedHandler;
import com.sidenow.global.config.jwt.filter.JwtAuthenticationEntryPoint;
import com.sidenow.global.config.jwt.filter.JwtFilter;
import com.sidenow.global.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // h2 database, Swagger 접속이 원활하도록 관련 API 전부 무시
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("webSecurityCustomizer 진입");

        return web -> web.ignoring()
                .requestMatchers("/h2-console/**",
                        "/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui/**");
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        log.info("filterChain 진입");
        http

                // 서버에 인증정보를 저장하지 않으므로 csrf 사용 X
                .csrf(AbstractHttpConfigurer::disable)

                // form 기반의 로그인 비활성화하며, 커스텀으로 구성한 필터 사용
                .formLogin(AbstractHttpConfigurer::disable)

                // 요청에 대한 인가 설정
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/api/auth/**").permitAll() // 로그인, 회원가입 API는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                        .requestMatchers(toH2Console()).permitAll() // h2-console 접속시키기
                        .anyRequest().authenticated()) // 그 외 나머지 API는 전부 인증 필요

                // JWT를 활용하면 세션이 필요 없으므로, STATELESS 설정 (Security는 기본적으로 세션을 사용함)
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // exception handling 할 때 자체로 만든 클래스 추가
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))

                // JWT 필터 추가
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)

                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))); //헤더의 값으로 sameorigin을 설정하면 frame에 포함된 페이지가 페이지를 제공하는 사이트와 동일한 경우에는 계속 사용 가능
        log.info("filterChain 종료");

        return http.build();
    }
}
