package com.sidenow.global.config.security.service;

import com.sidenow.global.dto.Oauth2Attribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.sidenow.domain.member.constant.MemberConstant.Role.Role_USER;

@Service
@Slf4j
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest memberRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2Member = oAuth2UserService.loadUser(memberRequest);
        String registrationId = memberRequest.getClientRegistration().getRegistrationId();
        String memberNameAttributeName = memberRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Oauth2Attribute oauth2Attribute = Oauth2Attribute.of(registrationId, memberNameAttributeName, oAuth2Member.getAttributes());

        log.info("{}", oauth2Attribute);

        var memberAttribute = oauth2Attribute.convertToMap();

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(String.valueOf(Role_USER))), memberAttribute, "email");
    }
}
