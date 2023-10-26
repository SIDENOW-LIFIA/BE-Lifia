package com.sidenow.domain.member.service;

import com.sidenow.domain.member.dto.res.MemberResponse;
import com.sidenow.domain.member.dto.res.MemberResponse.MemberInfoResponse;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberNotLoginException;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberMyPageServiceImpl implements MemberMyPageService{

    private final MemberRepository memberRepository;
    private final SecurityUtils securityUtils;

    @Override
    public MemberInfoResponse getMemberInfo() {
        log.info("Get Member Info Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId()).get();
        log.info("Get Member Info Service 종료");

        return MemberInfoResponse.from(member);
    }


}
