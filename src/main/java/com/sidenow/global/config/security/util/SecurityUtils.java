package com.sidenow.global.config.security.util;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.config.security.exception.RequiredLoggedInException;
import com.sidenow.global.config.security.service.CustomMemberDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class SecurityUtils {

    public static Member getLoggedInMember() {
        try{
            return ((CustomMemberDetails) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal()).getMember();
        } catch (NullPointerException e){
            throw new RequiredLoggedInException();
        }
    }

}
