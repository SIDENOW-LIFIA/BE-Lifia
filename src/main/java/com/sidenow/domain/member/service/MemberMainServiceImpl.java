package com.sidenow.domain.member.service;


import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.dto.MemberDto;
import com.sidenow.domain.member.exception.NotFoundEmailException;
import com.sidenow.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sidenow.domain.member.constant.MemberConstant.MemberServiceMessage.EXISTED_NICKNAME;
import static com.sidenow.domain.member.constant.MemberConstant.MemberServiceMessage.VALID_NICKNAME;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberMainServiceImpl implements MemberMainService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDto.CheckNicknameResponse checkNickname(String nickname) {
        if (this.memberRepository.findByNickname(nickname.trim()).isPresent()) {
            return new MemberDto.CheckNicknameResponse(EXISTED_NICKNAME.getValue());
        } else{
            return new MemberDto.CheckNicknameResponse(VALID_NICKNAME.getValue());
        }
    }

    @Override
    public Member validateEmail(String email) {
        return this.memberRepository.findByEmail(email).orElseThrow(() -> new NotFoundEmailException());
    }

}
