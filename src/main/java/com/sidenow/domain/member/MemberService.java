package com.sidenow.domain.member;

import com.sidenow.common.exception.NoExistMemberException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public void join(Member member){
        logger.info("SignUp Service Start");
        memberRepository.save(member);
        logger.info("SignUp Service End");
    }

    // 회원삭제
    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NoExistMemberException::new);
        memberRepository.delete(member);
    }

    // 이메일 중복검사
    public boolean validateDuplicateEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        return findMember.isEmpty();
    }

    // 닉네임 중복검사
    public boolean validateDuplicateNickname(String nickname) {
        Optional<Member> findMember = memberRepository.findByNickname(nickname);
        return findMember.isEmpty();
    }
}
