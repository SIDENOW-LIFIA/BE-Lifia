package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.req.MemberRequest;
import com.sidenow.domain.member.dto.req.MemberRequest.MemberLoginRequest;
import com.sidenow.domain.member.dto.req.MemberRequest.MemberTokenRequest;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberLoginResponse;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.entity.RefreshToken;
import com.sidenow.domain.member.exception.MemberEmailNotFoundException;
import com.sidenow.domain.member.exception.MemberLoginFailureException;
import com.sidenow.domain.member.exception.MemberNotExistException;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.domain.member.repository.RefreshTokenRepository;
import com.sidenow.global.config.jwt.TokenProvider;
import com.sidenow.global.config.redis.exception.NoSuchRefreshTokenException;
import com.sidenow.global.config.redis.repository.RedisRepository;
import com.sidenow.global.config.jwt.TokenResponse;
import com.sidenow.global.config.security.util.SecurityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberAuthServiceImpl implements MemberAuthService{

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;
    private final SecurityUtils securityUtils;
    private final PasswordEncoder passwordEncoder;

    // 로그인
    @Override
    public MemberLoginResponse login(MemberLoginRequest req){
        log.info("Login Service 진입");
        // 이메일 가입 여부 확인
        Member member = memberRepository.findByEmail(req.getEmail()).orElseThrow(MemberEmailNotFoundException::new);
        // 비밀번호 일치 여부 확인
        validatePassword(req, member);
        Authentication authentication = getMemberAuthentication(req);
        TokenResponse tokenResponse = tokenProvider.createToken(authentication);
        RefreshToken refreshToken = buildRefreshToken(authentication, tokenResponse);
        refreshTokenRepository.save(refreshToken);
        member.updateLoginAt(LocalDateTime.now());
        log.info("Login Service 종료");
        return MemberLoginResponse.from(tokenResponse, member);
    }

    // 토큰 만료 시 재발행
//    @Override
//    public MemberLoginResponse reIssueToken(MemberTokenRequest req){
//
//        log.info("reIssueToken Service 진입");
//        validateRefreshToken(req);
//        Authentication authentication = tokenProvider.getAuthentication(req.getAccessToken());
//        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
//                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
//
//        validateRefreshTokenOwner(refreshToken, req);
//
//        TokenResponse tokenResponse = tokenProvider.createToken(authentication);
//        RefreshToken newRefreshToken = refreshToken.updateValue(tokenResponse.getRefreshToken());
//        refreshTokenRepository.save(newRefreshToken);
//        log.info("reIssueToken Service 종료(토큰 재발급 및 저장 완료)");
//
//        return MemberLoginResponse.from(tokenResponse);
//    }

    private TokenResponse validateLogin(MemberLoginRequest req) {
        log.info("회원 아이디/비밀번호 일치여부 확인 및 토큰 발행 Start");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());

        // 아이디 / 비밀번호 일치여부 확인
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("회원 아이디/비밀번호 일치여부 확인 완료");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenResponse tokenResponse = tokenProvider.createToken(authentication);
        log.info("회원 아이디/비밀번호 일치여부 확인 및 토큰 발행 End");

        return tokenResponse;
    }

    private void validatePassword(MemberLoginRequest req, Member member) {
        log.info("비밀번호 검증 Start");
        if (!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new MemberLoginFailureException();
        }
        log.info("비밀번호가 일치합니다.");
    }

    private Authentication getMemberAuthentication(MemberLoginRequest req) {
        UsernamePasswordAuthenticationToken authenticationToken = req.toAuthentication();
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }

    private RefreshToken buildRefreshToken(Authentication authentication, TokenResponse tokenResponse) {
        return RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenResponse.getRefreshToken())
                .build();
    }

    private void validateRefreshToken(MemberTokenRequest req) {
        if (!tokenProvider.validateToken(req.getRefreshToken())) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }
    }

    private void validateRefreshTokenOwner(RefreshToken refreshToken, MemberTokenRequest req) {
        if (!refreshToken.getValue().equals(req.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }
    }

}
