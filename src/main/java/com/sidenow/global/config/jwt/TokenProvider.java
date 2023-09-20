package com.sidenow.global.config.jwt;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberNotExistException;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.jwt.exception.*;
import com.sidenow.global.config.jwt.service.CustomMemberDetails;
import com.sidenow.global.config.redis.repository.RedisRepository;
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
// JWT 토큰 관련 암호화, 복호화, 검증 로직
public class TokenProvider implements InitializingBean {

    private final MemberRepository memberRepository;
    private final RedisRepository redisRepository;
    private Key key;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String MEMBER_ID = "memberId";
    private static final String ACCESS = "Access";
    private static final String REFRESH = "Refresh";
    private static final String GRANT_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

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
    public TokenInfoResponse createToken(Authentication authentication) {

        log.info("createToken 진입");

        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();

        CustomMemberDetails principal = (CustomMemberDetails) authentication.getPrincipal();
        Long memberId = principal.getMember().getMemberId();
        String accessToken = createAccessToken(authorities, now, memberId);
        String refreshToken = createRefreshToken(authorities, now, memberId);

        updateRefreshToken(memberId, refreshToken);
        log.info("createToken 종료");

        return TokenInfoResponse.from(GRANT_TYPE, accessToken, refreshToken, ACCESS_TOKEN_EXPIRE_TIME);
    }

    // Refresh Token 업데이트
    public void updateRefreshToken(Long memberId, String refreshToken) {
        log.info("updateRefreshToken 진입");
        try {
            redisRepository.setValues(String.valueOf(memberId), refreshToken, Duration.ofSeconds(REFRESH_TOKEN_EXPIRE_TIME));
            log.info("updateRefreshToken 종료");
        } catch (NoSuchElementException e) {
            log.error("일치하는 회원이 없습니다.");
            throw e;
        }
    }

    public boolean validateToken(String token) {

        log.info("validateToken 진입");
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            log.info("validateToken 종료");
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new MalformedException();
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new ExpiredException();
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new UnsupportedException();
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new IllegalException();
        } catch (NoSuchElementException e) {
            log.error("유효하지 않은 JWT입니다.");
            throw e;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    // Refresh Token 검증 함수
    public void validateRefreshToken(String token) {

        log.info("validateRefreshToken 진입");
        if (!getSubject(token).equals(REFRESH)) {
            throw new IllegalArgumentException("Refresh Token이 아닙니다.");
        }
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            String memberId = getMemberId(token);
            String redisToken = redisRepository.getValues(memberId).orElseThrow();
            if (!redisToken.equals(token)) {
                throw new IllegalArgumentException("요청 Refresh Token이 서버의 Refresh Token과 다릅니다.");
            }
            log.info("validateRefreshToken 종료");
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw e;
        } catch (ExpiredJwtException e) {
            log.info("만료된  JWT 토큰입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw e;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw e;
        } catch (NoSuchElementException e) {
            log.error("유효하지 않은 JWT입니다.");
            throw e;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    public String getMemberId(String accessToken) {

        log.info("getMemberId 진입");
        Claims claims = parseClaims(accessToken);
        log.info("getMemberId 종료");

        return claims.get(MEMBER_ID).toString();
    }

    // JWT 토큰에서 만료시간을 확인하고, 현재 시간과 비교하여 남은 시간을 반환
    public Long getExpiration(String accessToken) {

        log.info("getExpiration 진입");
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        long now = new Date().getTime();
        log.info("getExpiration 종료");

        return (expiration.getTime() - now);
    }

    // redis blackList 확인
    public boolean checkBlackList(String token) {

        log.info("checkBlackList 진입");
        if (redisRepository.checkBlackList(token).isPresent()) {
            throw new IllegalArgumentException("이미 로그아웃된 유저입니다.");
        }
        log.info("checkBlackList 종료");

        return true;
    }

    public void checkMultiLogin(String token) {

        log.info("checkMultiLogin 진입");
        Claims claims = parseClaims(token);
        String memberId = claims.get(MEMBER_ID).toString();

        if (!redisRepository.getValues(ACCESS + memberId)
                .orElseThrow()
                .equals(token)) {
            throw new RemovedAccessTokenException();
        } else {
            log.info("checkMultiLogin 종료");
        }
    }

    // Access Token 생성
    private String createAccessToken(String authorities, Date now, Long memberID) {

        log.info("createAccessToken 진입");
        Date accessTokenValidity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);

        String accessToken = Jwts.builder()
                .setSubject(ACCESS)
                .claim(AUTHORITIES_KEY, authorities)
                .claim(MEMBER_ID, memberID)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(accessTokenValidity)
                .compact();

        log.info("Access Token 생성 완료");
        redisRepository.setValues(ACCESS + memberID.toString(), accessToken, Duration.ofSeconds(ACCESS_TOKEN_EXPIRE_TIME));
        log.info("Access Token Redis에 저장 완료");

        log.info("createAccessToken 종료");
        return accessToken;
    }

    // Refresh Token 생성
    private String createRefreshToken(String authorities, Date now, Long memberId) {

        log.info("createRefreshToken 진입");
        Date refreshTokenValidity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME);

        String refreshToken = Jwts.builder()
                .setSubject(REFRESH)
                .claim(AUTHORITIES_KEY, authorities)
                .claim(MEMBER_ID, memberId)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(refreshTokenValidity)
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



    // 인증하는 함수 (Token에 담겨있는 정보를 이용해 Authentication 객체 리턴)
    public Authentication getAuthentication(String token) {

        log.info("getAuthentication 진입");

        // 토큰 복호화
        Claims claims = parseClaims(token);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        String memberId = String.valueOf(claims.get(MEMBER_ID));
        Member member = this.memberRepository.findById(Long.parseLong(memberId)).orElseThrow(MemberNotExistException::new);
        log.info("getAuthentication 종료");

        return new UsernamePasswordAuthenticationToken(new CustomMemberDetails(member), token, authorities);
    }




}
