package com.sidenow.domain.boardType.free.comment.service;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.exception.NotFoundFreeBoardPostIdException;
import com.sidenow.domain.boardType.free.board.repository.FreeBoardRepository;
import com.sidenow.domain.boardType.free.comment.dto.req.FreeBoardCommentRequest.RegisterFreeBoardCommentRequest;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.FreeBoardCommentCheck;
import com.sidenow.domain.boardType.free.comment.dto.res.FreeBoardCommentResponse.FreeBoardGetCommentListResponse;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.boardType.free.comment.exception.FreeBoardCommentAuthErrorException;
import com.sidenow.domain.boardType.free.comment.exception.NotFoundFreeBoardCommentIdException;
import com.sidenow.domain.boardType.free.comment.repository.FreeBoardCommentRepository;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberNotExistException;
import com.sidenow.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class FreeBoardCommentServiceImpl implements FreeBoardCommentService{

    private final MemberRepository memberRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardCommentRepository freeBoardCommentRepository;

    @Override
    // 자유게시판 게시글 댓글 등록
    public FreeBoardCommentCheck registerFreeBoardComment(Long freeBoardPostId, RegisterFreeBoardCommentRequest registerFreeBoardCommentRequest) {
        log.info("Create FreeBoard Comment Service Start");
        FreeBoardCommentCheck freeBoardCommentCheck = new FreeBoardCommentCheck();
        Member findMember = memberRepository.findById(registerFreeBoardCommentRequest.getMemberId()).orElseThrow(MemberNotExistException::new);
        FreeBoard findFreeBoardPost = freeBoardRepository.findByFreeBoardPostId(freeBoardPostId).orElseThrow(NotFoundFreeBoardPostIdException::new);
        FreeBoardComment freeBoardComments;
        if (registerFreeBoardCommentRequest.getParentId() == null) {
            freeBoardComments = createFreeBoardParentComments(registerFreeBoardCommentRequest, findMember, findFreeBoardPost);
        } else {
            freeBoardComments = createFreeBoardChildComments(registerFreeBoardCommentRequest, findMember, findFreeBoardPost);
        }
        freeBoardCommentRepository.save(freeBoardComments);
        freeBoardCommentCheck.setSaved(true);
        log.info("Create FreeBoard Comment Service End");
        return freeBoardCommentCheck;
    }

    @Override
    // 자유게시판 게시글의 댓글 전체 조회
    public List<FreeBoardGetCommentListResponse> getFreeBoardCommentList(Long freeBoardPostId) {
        log.info("Read FreeBoard Comment Service Start");
        FreeBoard findFreeBoardPost = freeBoardRepository.findByFreeBoardPostId(freeBoardPostId).orElseThrow(NotFoundFreeBoardPostIdException::new);
        List<FreeBoardComment> freeBoardCommentsList = freeBoardCommentRepository.findAllByFreeBoard_FreeBoardPostIdOrderByCreatedAtAsc(findFreeBoardPost.getId());
        List<FreeBoardGetCommentListResponse> readFreeBoardCommentDto = new ArrayList<>();
        freeBoardCommentsList.forEach(s -> readFreeBoardCommentDto.add(FreeBoardGetCommentListResponse.from(s))); // 댓글 전체 조회 핵심 코드 (람다식 forEach 사용)
        log.info("Read FreeBoard Comment Service Start");
        return readFreeBoardCommentDto;
    }

    @Override
    // 자유게시판 게시글의 댓글 삭제
    public FreeBoardCommentCheck deleteFreeBoardComment(Long freeBoardPostId, Long freeBoardCommentId) {
        log.info("Delete FreeBoard Comment Service Start");
        FreeBoardCommentCheck freeBoardCommentCheck = new FreeBoardCommentCheck();
        freeBoardRepository.findByFreeBoardPostId(freeBoardPostId).orElseThrow(NotFoundFreeBoardPostIdException::new);
        FreeBoardComment findFreeBoardComment = freeBoardCommentRepository.findById(freeBoardCommentId).orElseThrow(NotFoundFreeBoardCommentIdException::new);
        findFreeBoardComment.changeIsDeleted(true);
        freeBoardCommentRepository.deleteById(findFreeBoardComment.getCommentId()); // 댓글 삭제
        freeBoardCommentCheck.setDeleted(true);
        log.info("Delete FreeBoard Comment Service End");
        return freeBoardCommentCheck;
    }

    @Override
    // 자유게시판 게시글의 댓글 수정
    public FreeBoardCommentCheck modifyFreeBoardComment(Long freeBoardPostId, Long freeBoardCommentId, RegisterFreeBoardCommentRequest registerFreeBoardCommentRequest) {
        log.info("Modify FreeBoard Comment Service Start");
        FreeBoardCommentCheck freeBoardCommentCheck = new FreeBoardCommentCheck();
        Member findMember = memberRepository.findById(registerFreeBoardCommentRequest.getMemberId()).orElseThrow(MemberNotExistException::new);
        freeBoardRepository.findByFreeBoardPostId(freeBoardPostId).orElseThrow(NotFoundFreeBoardPostIdException::new);
        FreeBoardComment findFreeBoardComment = freeBoardCommentRepository.findById(freeBoardCommentId).orElseThrow(NotFoundFreeBoardCommentIdException::new);
        if (findFreeBoardComment.getIsDeleted()) {
            throw new NotFoundFreeBoardCommentIdException();
        }
        if (!findFreeBoardComment.getMember().getMemberId().equals(findMember.getMemberId())){
            throw new FreeBoardCommentAuthErrorException();
        }
        FreeBoardComment newFreeBoardComment = findFreeBoardComment.updateContent(registerFreeBoardCommentRequest.getContent());
        newFreeBoardComment.setUpdatedAt(LocalDateTime.now());
        freeBoardCommentRepository.save(newFreeBoardComment);
        freeBoardCommentCheck.setUpdated(true);
        log.info("Modify FreeBoard Comment Service End");
        return freeBoardCommentCheck;
    }

    // 자식 댓글 등록 (대댓글)
    private FreeBoardComment createFreeBoardChildComments(RegisterFreeBoardCommentRequest createFreeBoardCommentRequest, Member member, FreeBoard freeBoard) {
        FreeBoardComment parent = freeBoardCommentRepository.findById(createFreeBoardCommentRequest.getParentId()).orElseThrow(NotFoundFreeBoardCommentIdException::new);

        return FreeBoardComment.builder()
                .member(member)
                .regDate(LocalDateTime.now())
                .freeBoard(freeBoard)
                .isDeleted(false)
                .content(createFreeBoardCommentRequest.getContent())
                .parent(parent)
                .build();
    }

    // 부모 댓글 등록 (댓글)
    private FreeBoardComment createFreeBoardParentComments(RegisterFreeBoardCommentRequest createFreeBoardCommentRequest, Member member, FreeBoard freeBoard) {

        return FreeBoardComment.builder()
                .member(member)
                .regDate(LocalDateTime.now())
                .freeBoard(freeBoard)
                .isDeleted(false)
                .content(createFreeBoardCommentRequest.getContent())
                .build();
    }
}
