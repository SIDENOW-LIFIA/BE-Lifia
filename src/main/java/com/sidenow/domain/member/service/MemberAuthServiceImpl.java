package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.req.MemberRequest.MemberLoginRequest;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberLoginResponse;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberEmailNotFoundException;
import com.sidenow.domain.member.exception.MemberNotExistException;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.jwt.TokenProvider;
import com.sidenow.global.config.redis.exception.NoSuchRefreshTokenException;
import com.sidenow.global.config.redis.repository.RedisRepository;
import com.sidenow.global.config.jwt.TokenInfoResponse;
import com.sidenow.global.config.security.util.SecurityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.lettuce.core.RedisException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberAuthServiceImpl implements MemberAuthService{

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;
    private final SecurityUtils securityUtils;

    // 로그인
    @Override
    public MemberLoginResponse login(MemberLoginRequest memberLoginRequest){
        log.info("Login Service Start");
        try {
            TokenInfoResponse tokenInfoResponse = validateLogin(memberLoginRequest);
            String memberEmail = memberLoginRequest.getEmail();
            Member member = memberRepository.findByEmail(memberEmail).orElseThrow(MemberEmailNotFoundException::new);
            LocalDate now = LocalDate.now();

            member.updateLoginAt(now);

            log.info("Login Service End");
            return MemberLoginResponse.from(tokenInfoResponse);
        } catch (AuthenticationException e) {
            throw e;
        }
    }

    // 로그아웃
    @Override
    public void logout(String authorization){
        log.info("Logout Service Start");
        String accessToken = authorization.substring(7);
        String memberId = tokenProvider.getMemberId(accessToken);

        try {
            Long expiration = tokenProvider.getExpiration(accessToken);

            // Access Token 남은 시간동안 블랙리스트
            redisRepository.setValues("blackList:" + accessToken, accessToken, Duration.ofSeconds(expiration));
            log.info("Logout Service End");

        } catch (ExpiredJwtException e) {
        } finally {
            redisRepository.deleteValues(String.valueOf(memberId));
        }
    }

    // 토큰 만료 시 재발행
    @Override
    public MemberLoginResponse reIssueToken(){

        log.info("reIssueToken Service 진입");
        Long memberId = securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotExistException::new).getMemberId();

        String refreshToken = redisRepository.getValues(memberId.toString()).orElseThrow(NoSuchRefreshTokenException::new);
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(authentication);
        log.info("reIssueToken Service 종료(토큰 재발급 및 저장 완료)");

        return MemberLoginResponse.from(tokenInfoResponse);
    }

    private TokenInfoResponse validateLogin(MemberLoginRequest memberLoginRequest) {
        log.info("회원 아이디/비밀번호 일치여부 확인 및 토큰 발행 Start");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberLoginRequest.getEmail(), memberLoginRequest.getPassword());

        // 아이디 / 비밀번호 일치여부 확인
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("회원 아이디/비밀번호 일치여부 확인 완료");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(authentication);
        log.info("회원 아이디/비밀번호 일치여부 확인 및 토큰 발행 End");

        return tokenInfoResponse;
    }

}
