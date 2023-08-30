package com.sidenow.member;

import com.sidenow.domain.member.dto.req.MemberRequest.MemberLoginRequest;
import com.sidenow.domain.member.dto.res.MemberResponse;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.domain.member.service.MemberAuthServiceImpl;
import com.sidenow.global.config.jwt.TokenProvider;
import com.sidenow.global.config.jwt.TokenInfoResponse;
import com.sidenow.utils.MockMember;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class MemberAuthServiceSuccessTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenProvider tokenProvider;
//    @Mock
//    private RedisRepository redisRepository;
    @InjectMocks
    private MemberAuthServiceImpl memberAuthServiceImpl;

    private MemberLoginRequest memberLoginRequest;
    private Member member = new MockMember();

    @BeforeEach
    void setUp() {
        memberLoginRequest = new MemberLoginRequest(member.getEmail(), member.getPassword());
    }

    @Test
    void loginTest() {
        given(memberRepository.findByEmail(member.getEmail())).willReturn(Optional.of(member));

        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getMemberId(), member.getPassword());
        given(authenticationManagerBuilder.getObject()).willReturn(authenticationManager);
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).willReturn(authentication);

        TokenInfoResponse tokenInfoResponse = new TokenInfoResponse("Bearer", "access_token", "refresh_token", 3000L);
        given(tokenProvider.createToken(any(Authentication.class))).willReturn(tokenInfoResponse);

        MemberResponse.MemberLoginResponse loginResponse = memberAuthServiceImpl.login(memberLoginRequest);

        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getAccessToken()).isEqualTo(tokenInfoResponse.getAccessToken());
        assertThat(loginResponse.getRefreshToken()).isEqualTo(tokenInfoResponse.getRefreshToken());
    }

}
