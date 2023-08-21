package com.sidenow.domain.boardType.free.comment.service;

import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.exception.FreeBoardException;
import com.sidenow.domain.boardType.free.board.exception.NotFoundFreeBoardPostIdException;
import com.sidenow.domain.boardType.free.board.repository.FreeBoardRepository;
import com.sidenow.domain.boardType.free.comment.dto.req.FreeBoardCommentRequest.CreateFreeBoardCommentRequest;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.CreateFreeBoardCommentResponse;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.ReadFreeBoardCommentDetailResponse;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.boardType.free.comment.exception.NotFoundFreeBoardCommentIdException;
import com.sidenow.domain.boardType.free.comment.repository.FreeBoardCommentRepository;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.NotFoundMemberException;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.security.util.SecurityUtils;
import com.sidenow.global.exception.NoExistMemberException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class FreeBoardCommentService {

    private final MemberRepository memberRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardCommentRepository freeBoardCommentRepository;
    private int commentCount;

    // 자유게시판 게시글 댓글 등록
    public CreateFreeBoardCommentResponse createFreeBoardComments(Long freeBoardPostId, CreateFreeBoardCommentRequest createFreeBoardCommentRequest) {
        Member member = memberRepository.findById(createFreeBoardCommentRequest.getMemberId()).orElseThrow(NotFoundMemberException::new);
        FreeBoard freeBoard = freeBoardRepository.findByPostId(freeBoardPostId).orElseThrow(NotFoundFreeBoardPostIdException::new);
        FreeBoardComment freeBoardComments;
        if (createFreeBoardCommentRequest.getParentId() == null) {
            freeBoardComments = createFreeBoardParentComments(createFreeBoardCommentRequest, member, freeBoard);
        } else {
            freeBoardComments = createFreeBoardChildComments(createFreeBoardCommentRequest, member, freeBoard);
        }

        freeBoardCommentRepository.save(freeBoardComments);
        return new CreateFreeBoardCommentResponse(freeBoardComments, member, freeBoard);
    }

    // 자유게시판 게시글의 댓글 조회
    public List<ReadFreeBoardCommentDetailResponse> readFreeBoardComments(Long freeBoardPostId) {
        // memberRepository.findById(SecurityUtils.getLoggedInMember().getMemberId()).orElseThrow(NoExistMemberException::new);

        freeBoardRepository.findByPostId(freeBoardPostId).orElseThrow(NotFoundFreeBoardPostIdException::new);
        List<FreeBoardComment> freeBoardCommentsList = freeBoardCommentRepository.findAllByFreeBoard_FreeBoardPostIdOrderByCreatedAtAsc(freeBoardPostId);
        List<ReadFreeBoardCommentDetailResponse> result = freeBoardCommentsList.stream().map(ReadFreeBoardCommentDetailResponse::new).collect(Collectors.toList());


        return
    }

    // 자유게시판 게시글 상세 조회
    public FreeBoardResponse.ReadFreeBoardPostDetailResponse readFreeBoardPostDetail(Long postId) {
        FreeBoard freeBoard = freeBoardRepository.findByPostId(postId).orElseThrow(NotFoundFreeBoardPostIdException::new);
        return FreeBoardResponse.ReadFreeBoardPostDetailResponse.from(freeBoard);
    }

    // 자식 댓글 등록 (대댓글)
    private FreeBoardComment createFreeBoardChildComments(CreateFreeBoardCommentRequest createFreeBoardCommentRequest, Member member, FreeBoard freeBoard) {
        FreeBoardComment parent = freeBoardCommentRepository.findById(createFreeBoardCommentRequest.getParentId()).orElseThrow(NotFoundFreeBoardCommentIdException::new);

        return FreeBoardComment.builder()
                .member(member)
                .freeBoard(freeBoard)
                .isDeleted(false)
                .content(createFreeBoardCommentRequest.getContent())
                .parent(parent)
                .build();
    }

    // 부모 댓글 등록 (댓글)
    private FreeBoardComment createFreeBoardParentComments(CreateFreeBoardCommentRequest createFreeBoardCommentRequest, Member member, FreeBoard freeBoard) {

        return FreeBoardComment.builder()
                .member(member)
                .freeBoard(freeBoard)
                .isDeleted(false)
                .content(createFreeBoardCommentRequest.getContent())
                .build();
    }
}
