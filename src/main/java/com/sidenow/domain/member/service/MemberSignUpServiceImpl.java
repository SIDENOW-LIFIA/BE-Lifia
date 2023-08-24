package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.req.MemberRequest;
import com.sidenow.domain.member.dto.res.MemberResponse;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.sidenow.domain.member.constant.MemberConstant.Role.Role_USER;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberSignUpServiceImpl implements MemberSignUpService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    @Override
    public MemberResponse.MemberCheck signUp(MemberRequest.SignUpMemberRequest signUpMemberRequest) {
        log.info("Sign Up Member Service Start");
        MemberResponse.MemberCheck memberCheck = new MemberResponse.MemberCheck();
        Member newMember = Member.builder()
                .email(signUpMemberRequest.getEmail())
                .password(passwordEncoder.encode(signUpMemberRequest.getPassword()))
                .name(signUpMemberRequest.getName())
                .nickname(signUpMemberRequest.getNickname())
                .address(signUpMemberRequest.getAddress())
                .role(Role_USER)
                .build();
        memberRepository.save(newMember);
        memberCheck.setSaved(true);
        log.info("Sign Up Member Service End");
        return memberCheck;
    }
}
