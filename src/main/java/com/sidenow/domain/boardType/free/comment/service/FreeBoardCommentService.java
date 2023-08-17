package com.sidenow.domain.boardType.free.comment.service;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.exception.NotFoundFreeBoardPostIdException;
import com.sidenow.domain.boardType.free.board.repository.FreeBoardRepository;
import com.sidenow.domain.boardType.free.comment.dto.req.FreeBoardCommentRequest.CreateFreeBoardCommentRequest;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.CreateFreeBoardCommentResponse;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.boardType.free.comment.exception.NotFoundFreeBoardCommentIdException;
import com.sidenow.domain.boardType.free.comment.repository.FreeBoardCommentRepository;
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
public class FreeBoardCommentService {

    private final MemberRepository memberRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardCommentRepository freeBoardCommentRepository;
    private int cnt;

    // 자유게시판 게시글 댓글 등록
    public CreateFreeBoardCommentResponse saveComments(Long freeBoardPostId, CreateFreeBoardCommentRequest createFreeBoardCommentRequest) {
        Member member = memberRepository.findById(SecurityUtils.getLoggedInMember().getMemberId()).orElseThrow(NoExistMemberException::new);
        FreeBoard freeBoard = freeBoardRepository.findByPostId(freeBoardPostId).orElseThrow(NotFoundFreeBoardPostIdException::new);
        FreeBoardComment freeBoardComments;
        if (createFreeBoardCommentRequest.getParentId() == null) {
            freeBoardComments = saveParentComments(createFreeBoardCommentRequest, member, freeBoard);
        } else {
            freeBoardComments = saveChildComments(createFreeBoardCommentRequest, member, freeBoard);
        }

        freeBoardCommentRepository.save(freeBoardComments);
        return new CreateFreeBoardCommentResponse(freeBoardComments.getCommentId());
    }

    // 자식 댓글 등록 (대댓글)
    private FreeBoardComment saveChildComments(CreateFreeBoardCommentRequest createFreeBoardCommentRequest, Member member, FreeBoard freeBoard) {
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
    private FreeBoardComment saveParentComments(CreateFreeBoardCommentRequest createFreeBoardCommentRequest, Member member, FreeBoard freeBoard) {

        return FreeBoardComment.builder()
                .member(member)
                .freeBoard(freeBoard)
                .isDeleted(false)
                .content(createFreeBoardCommentRequest.getContent())
                .build();
    }
}
