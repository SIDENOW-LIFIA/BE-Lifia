package com.sidenow.domain.boardType.free.board.service;

import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.CreateFreeBoardPostRequest;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.ReadFreeBoardPostDetailResponse;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.exception.NotFoundFreeBoardPostIdException;
import com.sidenow.domain.boardType.free.board.repository.FreeBoardRepository;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.security.util.SecurityUtils;
import com.sidenow.global.exception.NoExistMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class FreeBoardService {

    private final MemberRepository memberRepository;
    private final FreeBoardRepository freeBoardRepository;

    // 자유게시판 글 등록
    public void createPost(CreateFreeBoardPostRequest requestDto) {
        Member member = memberRepository.findById(SecurityUtils.getLoggedInMember().getMemberId()).orElseThrow(NoExistMemberException::new);
        FreeBoard freeBoard = CreateFreeBoardPostRequest.to(requestDto, member);
        freeBoardRepository.save(freeBoard);
    }

    // 자유게시판 게시글 상세 조회
    public ReadFreeBoardPostDetailResponse readPostDetail(Long postId) {
        FreeBoard freeBoard = freeBoardRepository.findByPostId(postId).orElseThrow(NotFoundFreeBoardPostIdException::new);
        return ReadFreeBoardPostDetailResponse.from(freeBoard);
    }
}
