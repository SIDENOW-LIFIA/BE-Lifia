package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.req.MemberRequest.SignUpMemberRequest;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberCheck;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberEmailDuplicateException;
import com.sidenow.domain.member.exception.MemberNicknameDuplicateException;
import com.sidenow.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    public Member signUp(SignUpMemberRequest req) {
        log.info("Sign Up Member Service 진입");
        MemberCheck memberCheck = new MemberCheck();
        LocalDate now = LocalDate.now();
        Member newMember = Member.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .nickname(req.getNickname())
                .apartment(req.getApartment())
                .role(Role_USER)
                .createdAt(now)
                .build();

        memberRepository.save(newMember);
        log.info("Sign Up Member Service 종료");
        return newMember;
    }

    // 이메일 중복 체크
    @Override
    public boolean checkEmailDuplicate(String email) {
        Optional<Member> findEmail = memberRepository.findByEmail(email);
        if (findEmail.isPresent()) {
            throw new MemberEmailDuplicateException();
        }
        return true;
    }

    // 닉네임 중복 체크
    @Override
    public boolean checkNicknameDuplicate(String nickname) {
        Optional<Member> findNickname = memberRepository.findByNickname(nickname);
        if (findNickname.isPresent()) {
            throw new MemberNicknameDuplicateException();
        }
        return true;
    }
}
