package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.req.MemberRequest;
import com.sidenow.domain.member.dto.req.MemberRequest.MemberLoginRequest;
import com.sidenow.domain.member.dto.res.MemberResponse;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberLoginResponse;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberEmailNotFoundException;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.jwt.TokenProvider;
import com.sidenow.global.dto.ResponseDto;
import com.sidenow.global.dto.TokenInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberAuthServiceImpl implements MemberAuthService{

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    // 로그인
    @Override
    public MemberLoginResponse login(MemberLoginRequest memberLoginRequest){
        try {
            TokenInfoResponse tokenInfoResponse = validateLogin(memberLoginRequest);
            String memberEmail = memberLoginRequest.getEmail();
            Member member = memberRepository.findByEmail(memberEmail).orElseThrow(MemberEmailNotFoundException::new);
            LocalDate now = LocalDate.now();

            member.updateLoginAt(now);

            return MemberLoginResponse.from(tokenInfoResponse);
        } catch (AuthenticationException e) {
            throw e;
        }
    }

    // 로그아웃
    @Override
    public void logout(String authorization){

    }

    // 토큰 만료 시 재발행
    @Override
    public MemberLoginResponse reIssueToken(){

    }

    private TokenInfoResponse validateLogin(MemberLoginRequest memberLoginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberLoginRequest.getEmail(), memberLoginRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(authentication);

        return tokenInfoResponse;
    }

}
