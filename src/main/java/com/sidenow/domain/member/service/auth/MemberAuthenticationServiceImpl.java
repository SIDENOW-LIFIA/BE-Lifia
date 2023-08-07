package com.sidenow.domain.member.service.auth;

import com.google.gson.JsonObject;
import com.sidenow.domain.member.dto.MemberDto.*;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.domain.member.service.kakao.MemberKakaoService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final MemberKakaoService kakaoService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final MemberValidationService validateService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member signUp(SignUpRequest signUpRequest) {
        Member member = Member.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .name(signUpRequest.getName())
                .nickname(signUpRequest.getNickname())
                .address(signUpRequest.getAddress())
                .role(Role_USER)
                .build();

        return memberRepository.save(member);
    }

    @Override
    public LoginResponse socialLoginSignUp(AdditionInfoRequest additionInfoRequest){
        // 추가 정보 입력 시
        // 1. 프론트에게 받은 (자체) 액세스 토큰 이용해서 사용자 이메일 가져오기
        Authentication authentication = tokenProvider.getAuthentication(additionInfoRequest.getAccessToken());
        Member member = validateService.validateEmail(authentication.getName());

        // 2. 추가 정보 저장
        member.setMember(additionInfoRequest.getPassword(),additionInfoRequest.getNickname(), additionInfoRequest.getName(), additionInfoRequest.getAddress());
        memberRepository.save(member);

        // 3. 스프링 시큐리티 처리
        List<GrantedAuthority> authorities = initAuthorities();
        OAuth2User memberDetails = createOAuth2UserByMember(authorities, member);
        OAuth2AuthenticationToken auth = configureAuthentication(memberDetails, authorities);

        // 4. JWT 토큰 생성
        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, true, member.getMemberId());
        return LoginResponse.from(tokenInfoResponse, LOGIN_SUCCESS.getMessage(), member.getMemberId());
    }

    @Override
    public LoginResponse kakaoLogin(LoginRequest loginRequest) {

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
        return LoginResponse.from(tokenInfoResponse, isSignedUp ? LOGIN_SUCCESS.getMessage() : SIGN_UP_ING.getMessage(), member.getMemberId());
    }


    @Override
    public void kakaoLogout(LoginRequest loginRequest){
        // 1. 카카오 로그아웃 처리
        String token = loginRequest.getToken();
        JsonObject response = kakaoService.connectKakao(LOGOUT_URL.getValue(), token);

        // 2. Redis에서 해당 사용자의 Refresh Token을 삭제
        Member member = validateService.validateEmail(SecurityUtils.getLoggedInMember().getEmail());
        refreshTokenRepository.deleteById(member.getMemberId());
    }

    @Override
    public void deleteAccount(DeleteAccountRequest deleteAccountRequest){

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
    public LoginResponse testLogin(TestLoginRequest testLoginRequest){
        Member member = new Member();
        member.setMember(testLoginRequest.getPassword(), testLoginRequest.getNickname(), testLoginRequest.getName(), testLoginRequest.getAddress());
        memberRepository.save(member);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(Role_USER)));
        OAuth2User memberDetails = createOAuth2UserByMember(authorities, member);
        OAuth2AuthenticationToken auth = configureAuthentication(memberDetails, authorities);

        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, true, member.getMemberId());
        return LoginResponse.from(tokenInfoResponse, LOGIN_SUCCESS.getMessage(), member.getMemberId());
    }

    public Member saveMember(String email) {
        Member member = Member.builder()
                .email(email)
                .role(Role_USER)
                .build();
        if (memberRepository.findByEmail(email).isEmpty()) {
            return memberRepository.save(member);
        }
        return memberRepository.findByEmail(email).get();
    }

    /**
     * Member -> OAuth2Member
     *
     * @param authorities
     * @param member
     * @return OAuth2User
     */
    public OAuth2User createOAuth2UserByMember(List<GrantedAuthority> authorities, Member member) {
        Map memberMap = new HashMap<String, String>();
        memberMap.put("email", member.getEmail());
        memberMap.put("password", member.getPassword());
        memberMap.put("nickname", member.getNickname());
        memberMap.put("name", member.getName());
        memberMap.put("address", member.getAddress());
        OAuth2User memberDetails = new DefaultOAuth2User(authorities, memberMap, "email");
        return memberDetails;
    }

    /**
     * memberInfo, email -> OAuth2User
     *
     * @param authorities
     * @param memberInfo
     * @param email
     * @return OAuth2User
     */
    private OAuth2User createOAuth2MemberByJson(List<GrantedAuthority> authorities, JsonObject memberInfo, String email) {
        Map memberMap = new HashMap<String, String>();
        memberMap.put("email", email);
        authorities.add(new SimpleGrantedAuthority(String.valueOf(Role_USER)));
        OAuth2User memberDetails = new DefaultOAuth2User(authorities, memberMap, "email");
        return memberDetails;
    }

    public List<GrantedAuthority> initAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(Role_USER)));
        return authorities;
    }

    public OAuth2AuthenticationToken configureAuthentication(OAuth2User memberDetails, List<GrantedAuthority> authorities) {
        OAuth2AuthenticationToken auth = new OAuth2AuthenticationToken(memberDetails, authorities, "email");
        auth.setDetails(memberDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }

}
