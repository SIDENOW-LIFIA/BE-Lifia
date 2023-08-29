package com.sidenow.global.config.security;

import com.amazonaws.services.ec2.model.ExcessCapacityTerminationPolicy;
import com.sidenow.global.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호를 암호화하기 위한 BCrypt 인코딩을 통하여 비밀번호에
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/h2-console/**",
                        "/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui/**");
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                // 서버에 인증정보를 저장하지 않으므로 csrf 사용 X
                .csrf(AbstractHttpConfigurer::disable)

                // form 기반의 로그인 비활성화하며, 커스텀으로 구성한 필터 사용
                .formLogin(AbstractHttpConfigurer::disable)

                // 요청에 대한 인가 설정
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.PUT, "/api/member/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/board/free/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/board/free/**").authenticated()
                        .requestMatchers("/api/member/logout").permitAll()
                        .anyRequest().permitAll())

                // JWT를 활용하면 세션이 필요 없으므로, STATELESS 설정
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore()


//                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll())// 인증되지 않은 요청을 허락함
//                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//                        .requestMatchers(AUTH_WHITELIST).authenticated())
//                .csrf((csrf) -> csrf
//                        .ignoringRequestMatchers(new AntPathRequestMatcher("/**"))) // /h2-console/로 시작하는 URL은 CSRF 검증을 하지 않는다는 설정 추가
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))); //헤더의 값으로 sameorigin을 설정하면 frame에 포함된 페이지가 페이지를 제공하는 사이트와 동일한 경우에는 계속 사용가능
        return http.build();
    }

}
