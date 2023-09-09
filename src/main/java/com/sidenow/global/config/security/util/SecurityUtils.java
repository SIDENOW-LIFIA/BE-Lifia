package com.sidenow.global.config.security.util;

import com.sidenow.domain.member.entity.Member;
import com.sidenow.global.config.jwt.service.CustomMemberDetails;
import com.sidenow.global.config.security.exception.RequiredLoggedInException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Objects;
import java.util.Optional;

@Component
public class SecurityUtils {

    public Optional<Member> getLoggedInMember() {

        return Optional.ofNullable(
                ((CustomMemberDetails) (SecurityContextHolder.getContext().getAuthentication()).getPrincipal()).getMember());
    }

}
