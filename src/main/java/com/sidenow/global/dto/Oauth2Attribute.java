package com.sidenow.global.dto;

import com.sidenow.domain.member.Member;
import com.sidenow.domain.member.constant.MemberConstant;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static com.sidenow.domain.member.constant.MemberConstant.Role.Role_USER;

@Getter
@Builder
public class Oauth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;

    public static Oauth2Attribute of(String provider, String attributeKey, Map<String, Object> attributes) {
        switch (provider){
            case "kakao":
                return ofKakao("id", attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static Oauth2Attribute ofKakao(String attributeKey, Map<String, Object> attributes) {
        return Oauth2Attribute.builder()
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .role(Role_USER)
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("email", email);

        return map;
    }


}
