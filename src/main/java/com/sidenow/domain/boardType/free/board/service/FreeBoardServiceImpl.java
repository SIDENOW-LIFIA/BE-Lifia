package com.sidenow.domain.boardType.free.board.service;

import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardCreatePostRequest;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.CreateFreeBoardPostResponse;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardCheck;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardGetPostResponse;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.exception.NotFoundFreeBoardPostIdException;
import com.sidenow.domain.boardType.free.board.repository.FreeBoardRepository;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class FreeBoardServiceImpl {

    private final MemberRepository memberRepository;
    private final FreeBoardRepository freeBoardRepository;

    // 자유게시판 글 등록
    public FreeBoardCheck createFreeBoardPost(FreeBoardCreatePostRequest createFreeBoardPostRequest) {
        Member member = memberRepository.findById()
        FreeBoard freeBoard = FreeBoardCreatePostRequest.to(createFreeBoardPostRequest, member);
        freeBoardRepository.save(freeBoard);
        return new FreeBoardCheck(freeBoard.getFreeBoardPostId());
    }

    // 자유게시판 게시글 상세 조회
    public FreeBoardGetPostResponse readFreeBoardPostDetail(Long postId) {
        FreeBoard freeBoard = freeBoardRepository.findByFreeBoardPostId(postId).orElseThrow(NotFoundFreeBoardPostIdException::new);
        return FreeBoardGetPostResponse.from(freeBoard);
    }

    // 자유게시판 게시글 전체 조회
}
