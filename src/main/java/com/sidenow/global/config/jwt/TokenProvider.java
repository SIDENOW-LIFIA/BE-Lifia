package com.sidenow.global.config.jwt;

import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.jwt.exception.*;
import com.sidenow.global.config.jwt.service.CustomMemberDetails;
import com.sidenow.global.config.jwt.service.CustomMemberDetailsService;
import com.sidenow.global.config.redis.repository.RedisRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
// JWT 토큰 생성, 토큰 복호화 및 정보 추출, 토큰 유효성 검증의 기능이 구현된 클래스
public class TokenProvider implements InitializingBean {

    private final RedisRepository redisRepository;
    private final CustomMemberDetailsService customMemberDetailsService;
    private Key key;
    private static final String AUTHORITIES_KEY = "auth";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String MEMBER_ID = "memberId";
    private static final String ACCESS = "Access ";
    private static final String REFRESH = "Refresh ";
    private static final String GRANT_TYPE = "Bearer";
    private static final String TOKEN_ISSUER = "Lifia";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분 (1분 * 30)
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일 (1시간 * 24 * 7)

    @Value("${spring.jwt.secret}")
    private String secret;

    // Bean 생성이 되고, 의존성 주입을 받은 후에 secret 값을 Base64 Decode해서 key 변수에 할당하기 위함.
    @Override
    public void afterPropertiesSet() {

        log.info("afterPropertiesSet 진입");
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        log.info("afterPropertiesSet 종료");
    }

    // 토큰 생성
    public TokenResponse createToken(Authentication authentication) {

        log.info("createToken 진입, name: "+authentication.getName());

        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();

        String accessToken = createAccessToken(authentication, authorities, now);
        String refreshToken = createRefreshToken(now);

        log.info("createToken 종료");

        return TokenResponse.from(GRANT_TYPE, accessToken, ACCESS_TOKEN_EXPIRE_TIME, refreshToken);
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {

        log.info("getAuthentication 진입");

        UserDetails userDetails = customMemberDetailsService.loadUserByUsername(getUsername(token));

        log.info("userDetails: "+userDetails);
        log.info("userDetails.getAuth: "+userDetails.getAuthorities());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원정보 추출
    private String getUsername(String token) {
        log.info("getUsername 진입");

        String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        log.info("subject: "+subject);
        return subject;
    }

    // Http Request의 Header에서 토큰 값을 가져오는 메서드
    public String resolveToken(HttpServletRequest request) {
        log.info("Resolve Token 진입");

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        log.info("BearerToken: "+bearerToken);

        if (bearerToken != null && bearerToken.startsWith(GRANT_TYPE)) {
            log.info("Claim 추출: "+bearerToken.substring(7));
            return bearerToken.substring(7); // "Bearer " 7글자 빼고 나머지 부분(Access Token) 추출 / 앞부분은 type임. 요청헤더의 구성은 Authorization: <type> <credentials>임
        } else {
            log.info("Request Header에 토큰 없음");
            return null;
        }
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {

        log.info("validateToken 진입");

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            log.info("validateToken 종료");

            return true;
        } catch (SignatureException e){
            log.info("잘못된 JWT 서명입니다. "+e.getMessage());
        } catch (MalformedJwtException e){
            log.info("잘못된 JWT 토큰입니다. "+e.getMessage());
        } catch (ExpiredJwtException e){
            log.info("만료된 JWT 토큰입니다."+e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다."+e.getMessage());
        } catch (IllegalArgumentException e) {
            log.info("JWT 클레임이 비어있습니다."+e.getMessage());
        }

        return false;
    }

    // JWT 토큰에서 만료시간을 확인하고, 현재 시간과 비교하여 남은 시간을 반환
    public Long getExpiration(String accessToken) {

        log.info("getExpiration 진입");
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        long now = new Date().getTime();
        log.info("getExpiration 종료");

        return (expiration.getTime() - now);
    }

    // Access Token 생성
    private String createAccessToken(Authentication authentication, String authorities, Date now) {

        log.info("createAccessToken 진입");
        Date accessTokenExpiresIn = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // 정보 저장
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuer(TOKEN_ISSUER) // 토큰 발급자
                .setIssuedAt(now) // 토큰 발행시간 정보
                .setExpiration(accessTokenExpiresIn) // 만료시간
                .signWith(key, SignatureAlgorithm.HS512) // 사용할 암호화 알고리즘과 signature에 들어갈 secret값 세팅
                .compact();

        log.info("createAccessToken 종료");
        return accessToken;
    }

    // Refresh Token 생성
    private String createRefreshToken(Date now) {

        log.info("createRefreshToken 진입");
        Date refreshTokenExpiresIn = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME);

        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        log.info("createRefreshToken 종료");

        return refreshToken;
    }

    // 주어진 JWT 토큰에서 클레임을 추출
    private Claims parseClaims(String accessToken) {

        log.info("parseClaims 진입");
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private String getSubject(String accessToken) {

        log.info("getSubject 진입");
        Claims claims = parseClaims(accessToken);
        log.info("getSubject 종료");

        return claims.getSubject();
    }
}
