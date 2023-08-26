//package com.sidenow.global.config.redis.service;
//
//import com.sidenow.domain.member.entity.Member;
//import com.sidenow.domain.member.exception.MemberNotExistException;
//import com.sidenow.domain.member.repository.MemberRepository;
//import com.sidenow.global.config.jwt.TokenProvider;
//import com.sidenow.global.config.redis.dto.RefreshTokenDto;
//import com.sidenow.global.config.redis.exception.NotFoundRefreshToken;
//import com.sidenow.global.config.redis.repository.RedisRepository;
//import com.sidenow.global.dto.TokenInfoResponse;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//@Transactional
//public class RefreshTokenServiceImpl implements RefreshTokenService {
//    private final RedisRepository redisRepository;
//    private final TokenProvider tokenProvider;
//    private final MemberRepository memberRepository;
////    private final MemberTempServiceImpl authenticationService;
//
//    @Override
//    public RefreshTokenDto.RefreshTokenResponse refreshToken(RefreshTokenDto.RefreshTokenRequest refreshTokenRequest) {
//
//        // 1. refreshToken 검증
//        redisRepository.findById(refreshTokenRequest.getMemberId()).orElseThrow(() -> new NotFoundRefreshToken());
//
//        // 2. 새로운 accessToken 재발급
//        // 2.1. 시큐리티 설정
//        Member member = memberRepository.findById(refreshTokenRequest.getMemberId()).orElseThrow(() -> new MemberNotExistException());
//        List<GrantedAuthority> authorities = authenticationService.initAuthorities();
//        OAuth2User memberDetails = authenticationService.createOAuth2UserByMember(authorities, member);
//        OAuth2AuthenticationToken auth = authenticationService.configureAuthentication(memberDetails, authorities);
//        // 2.2. JWT 토큰 생성
//        TokenInfoResponse tokenInfoResponse = tokenProvider.createToken(auth, true, member.getMemberId());
//        return RefreshTokenDto.RefreshTokenResponse.from(tokenInfoResponse);
//    }
//}
