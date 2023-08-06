package com.sidenow.domain.member.service.auth;

import com.google.gson.JsonObject;
import com.sidenow.domain.member.Member;
import com.sidenow.domain.member.constant.MemberConstant;
import com.sidenow.domain.member.dto.MemberDto;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.domain.member.service.kakao.MemberKakaoServiceImpl;
import com.sidenow.domain.member.service.validate.MemberValidationService;
import com.sidenow.global.config.jwt.TokenProvider;
import com.sidenow.global.config.redis.repository.RefreshTokenRepository;
import com.sidenow.global.config.security.util.SecurityUtils;
import com.sidenow.global.dto.TokenInfoResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sidenow.domain.member.constant.MemberConstant.MemberServiceMessage.*;
import static com.sidenow.domain.member.constant.MemberConstant.Process.LOGIN_SUCCESS;
import static com.sidenow.domain.member.constant.MemberConstant.Process.SIGN_UP_ING;
import static com.sidenow.domain.member.constant.MemberConstant.Role.Role_USER;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberAuthenticationServiceImpl implements MemberAuthenticationService{

    private final MemberRepository memberRepository;
    private final MemberKakaoServiceImpl kakaoService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final MemberValidationService validateService;

    @Override
    public MemberDto.LoginResponse login(MemberDto.LoginRequest loginRequest) {

        // 1. 프론트에게 받은 액세스 토큰을 이용해서 사용자 정보 가져오기
        String token = loginRequest.getToken();
        JsonObject memberInfo = kakaoService.connectKakao(LOGIN_URL.getValue(), token);
        Member member = saveMember(kakaoService.getEmail(memberInfo));
        boolean isSignedUp = member.getNickname() != null;

        // 2. 스프링 시큐리티 처리
        List<GrantedAuthority> authorities = initAuthorities();
        OAuth2User memberDetails = createOAuth2MemberByJson(authorities, memberInfo, kakaoService.getEmail(memberInfo));
        OAuth2AuthenticationToken auth = configureAuthentication(memberDetails, authorities);

        // 3. JWT 토큰 생성
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, isSignedUp, member.getMemberId());
        return MemberDto.LoginResponse.from(tokenInfoResponse, isSignedUp ? LOGIN_SUCCESS.getMessage() : SIGN_UP_ING.getMessage(), member.getMemberId());
    }

    @Override
    public MemberDto.LoginResponse signup(MemberDto.AdditionInfoRequest additionInfoRequest){
        // 추가 정보 입력 시
        // 1. 프론트에게 받은 (자체) 액세스 토큰 이용해서 사용자 이메일 가져오기
        Authentication authentication = tokenProvider.getAuthentication(additionInfoRequest.getAccessToken());
        Member member = validateService.validateEmail(authentication.getName());

        // 2. 추가 정보 저장
        member.setMember(additionInfoRequest.getNickname(), additionInfoRequest.getAddress());
        memberRepository.save(member);

        // 3. 스프링 시큐리티 처리
        List<GrantedAuthority> authorities = initAuthorities();
        OAuth2User memberDetails = createOAuth2MemberByMember(authorities, member);
        OAuth2AuthenticationToken auth = configureAuthentication(memberDetails, authorities);

        // 4. JWT 토큰 생성
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, true, member.getMemberId());
        return MemberDto.LoginResponse.from(tokenInfoResponse, LOGIN_SUCCESS.getMessage(), member.getMemberId());
    }
    @Override
    public void logout(MemberDto.LoginRequest loginRequest){
        // 1. 카카오 로그아웃 처리
        String token = loginRequest.getToken();
        JsonObject response = kakaoService.connectKakao(LOGOUT_URL.getValue(), token);

        // 2. Redis에서 해당 사용자의 Refresh Token을 삭제
        Member member = validateService.validateEmail(SecurityUtils.getLoggedInUser().getEmail());
        refreshTokenRepository.deleteById(member.getMemberId());
    }

    @Override
    public void deleteAccount(MemberDto.DeleteAccountRequest deleteAccountRequest){

        // 1. 카카오 회원탈퇴 처리
        String token = deleteAccountRequest.getToken();
        JsonObject response = kakaoService.connectKakao(DELETE_URL.getValue(), token);

        // 2. Redis에서 해당 사용자의 Refresh Token을 삭제
        Member member = validateService.validateEmail(SecurityUtils.getLoggedInMember().getEmail());
        refreshTokenRepository.deleteById(member.getMemberId());

        // 3. DB에서 삭제처리
        member.setDeleted(deleteAccountRequest.getReasonToLeave());
        memberRepository.save(member);
    }

    @Override
    public MemberDto.LoginResponse testLogin(MemberDto.TestLoginRequest testLoginRequest){
        Member member = new Member(testLoginRequest.getEmail(), Role_USER);
        member.setMember(testLoginRequest.getNickname(), testLoginRequest.getAddress());
        memberRepository.save(member);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(Role_USER)));
        OAuth2User memberDetails = createOAuth2MemberByMember(authorities, member);
        OAuth2AuthenticationToken auth = configureAuthentication(memberDetails, authorities);

        TokenInfoResponse tokenInfoResponse = tokenProvider.
    }
}
