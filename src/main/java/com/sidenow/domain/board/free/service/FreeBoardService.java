package com.sidenow.domain.board.free.service;

import com.sidenow.domain.board.free.dto.FreeBoardDto;
import com.sidenow.domain.board.free.entity.FreeBoard;
import com.sidenow.domain.board.free.repository.FreeBoardRepository;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.security.util.SecurityUtils;
import com.sidenow.global.exception.LifiaException;
import com.sidenow.global.exception.NoExistMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class FreeBoardService {

    private final MemberRepository memberRepository;
    private final FreeBoardRepository freeBoardRepository;

    // 자유게시판 글 등록
    public void save(FreeBoardDto.CreateFreeBoardRequest requestDto) {
        Member member = memberRepository.findById(SecurityUtils.getLoggedInMember().getMemberId()).orElseThrow(NoExistMemberException::new);
        FreeBoard freeBoard = FreeBoardDto.CreateFreeBoardRequest.to(requestDto, member);
        freeBoardRepository.save(freeBoard);
    }
}
