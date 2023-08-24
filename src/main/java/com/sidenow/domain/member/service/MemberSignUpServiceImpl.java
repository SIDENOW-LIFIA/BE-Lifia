package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.req.MemberRequest;
import com.sidenow.domain.member.dto.req.MemberRequest.SignUpMemberRequest;
import com.sidenow.domain.member.dto.res.MemberResponse;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberCheck;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sidenow.domain.member.constant.MemberConstant.Role.Role_USER;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberSignUpServiceImpl implements MemberSignUpService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    // 회원가입
    @Override
    public MemberCheck signUp(SignUpMemberRequest signUpMemberRequest) {
        log.info("Sign Up Member Service Start");
        MemberCheck memberCheck = new MemberCheck();
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

    // 이메일 중복 체크
    @Override
    public boolean checkEmailDuplicate(String email) {
        Optional<Member> findEmail = memberRepository.findByEmail(email);
        if (findEmail.isPresent()) {
            return false;
        }
        return true;
    }

    // 닉네임 중복 체크
    @Override
    public boolean checkNicknameDuplicate(String nickname) {
        Optional<Member> findNickname = memberRepository.findByNickname(nickname);
        if (findNickname.isPresent()) {
            return false;
        }
        return true;
    }
}
