package com.sidenow.global.config.jwt;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberNotExistException;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.jwt.exception.*;
import com.sidenow.global.config.redis.repository.RedisRepository;
import com.sidenow.global.config.security.service.CustomMemberDetails;
import com.sidenow.global.dto.TokenInfoResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

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
public class TokenProvider implements InitializingBean {

    private final MemberRepository memberRepository;
    private final RedisRepository redisRepository;
    private Key key;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String MEMBER_ID = "memberId";
    private static final String ACCESS = "Access";
    private static final String REFRESH = "Refresh";
    private static final String GRANT = "Bearer";

    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.jwt.access-token-validity-in-seconds}")
    private long accessTokenValidityTime;

    @Value("${spring.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityTime;

    // Bean 생성이 되고, 의존성 주입을 받은 후에 secret 값을 Base64 Decode해서 key 변수에 할당하기 위함.
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성
    public TokenInfoResponse createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        Long memberId = principal.getMember().getMemberId();
        String accessToken = createAccessToken(authorities, now, memberId);
        String refreshToken = createRefreshToken(authorities, now, memberId);

        updateRefreshToken(memberId, refreshToken);

        return TokenInfoResponse.from(GRANT, accessToken, refreshToken, accessTokenValidityTime);
    }

    public void updateRefreshToken(Long memberId, String refreshToken) {

        try {
            log.info("Refresh Token 저장");
            redisRepository.setValues(String.valueOf(memberId), refreshToken, Duration.ofSeconds(refreshTokenValidityTime));
        } catch (NoSuchElementException e) {
            log.error("일치하는 회원이 없습니다.");
            throw e;
        }
    }

    private String createAccessToken(String authorities, Date now, Long memberID) {
        Date accessTokenValidity = new Date(now.getTime() + this.accessTokenValidityTime);

        String accessToken = Jwts.builder()
                .setSubject(ACCESS)
                .claim(AUTHORITIES_KEY, authorities)
                .claim(MEMBER_ID, memberID)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessTokenValidity)
                .compact();

        log.info("Access Token 저장");
        redisRepository.setValues(ACCESS + memberID.toString(), accessToken, Duration.ofSeconds(accessTokenValidityTime));

        return accessToken;
    }

    private String createRefreshToken(String authorities, Date now, Long memberId) {

        Date refreshTokenValidity = new Date(now.getTime() + this.refreshTokenValidityTime);

        String refreshToken = Jwts.builder()
                .setSubject(REFRESH)
                .claim(AUTHORITIES_KEY, authorities)
                .claim(MEMBER_ID, memberId)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(refreshTokenValidity)
                .compact();

        return refreshToken;
    }

    // 인증하는 함수 (Token에 담겨있는 정보를 이용해 Authentication 객체 리턴)
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        Member member = this.memberRepository.findByEmail(claims.getSubject()).orElseThrow(MemberNotExistException::new);
        return new UsernamePasswordAuthenticationToken(new CustomMemberDetails(member), token, authorities);
    }

    /**
     * AccessToken 검증하는 함수
     * @param token
     * @return true / false
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new MalformedException();
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new ExpiredException();
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않은 JWT 토큰입니다.");
            throw new UnsupportedException();
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new IllegalException();
        } catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    /**
     * RefreshToken 검증
     *
     * @param token
     * @return true / false
     */
    public boolean validateRefreshToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new MalformedException();
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new ExpiredException();
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않은 JWT 토큰입니다.");
            throw new UnsupportedException();
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new IllegalException();
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new UnknownException();
        } finally {
            return false;
        }
    }

    /**
     * 주어진 JWT 토큰에서 클레임을 추출
     *
     * @param accessToken
     * @return Claims
     */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * JWT 토큰에서 만료시간을 확인하고, 현재 시간과 비교하여 남은 시간을 반환
     *
     * @param accessToken
     * @return 남은 시간
     */
    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }
}
