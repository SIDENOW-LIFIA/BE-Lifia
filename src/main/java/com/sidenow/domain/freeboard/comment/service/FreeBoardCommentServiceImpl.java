package com.sidenow.domain.freeboard.comment.service;

import com.sidenow.domain.freeboard.board.entity.FreeBoard;
import com.sidenow.domain.freeboard.board.exception.FreeBoardIdNotFoundException;
import com.sidenow.domain.freeboard.board.repository.FreeBoardRepository;
import com.sidenow.domain.freeboard.comment.dto.req.FreeBoardCommentRequest.FreeBoardCommentCreateRequest;
import com.sidenow.domain.freeboard.comment.dto.req.FreeBoardCommentRequest.FreeBoardCommentUpdateRequest;
import com.sidenow.domain.freeboard.comment.dto.res.FreeBoardCommentResponse.FreeBoardGetCommentListResponse;
import com.sidenow.domain.freeboard.comment.entity.FreeBoardComment;
import com.sidenow.domain.freeboard.comment.entity.FreeBoardCommentLike;
import com.sidenow.domain.freeboard.comment.exception.FreeBoardCommentLikeHistoryNotFoundException;
import com.sidenow.domain.freeboard.comment.exception.NotFoundFreeBoardCommentIdException;
import com.sidenow.domain.freeboard.comment.repository.FreeBoardCommentLikeRepository;
import com.sidenow.domain.freeboard.comment.repository.FreeBoardCommentRepository;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberNotExistException;
import com.sidenow.domain.member.exception.MemberNotLoginException;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.sidenow.domain.freeboard.comment.dto.res.FreeBoardCommentResponse.FreeBoardCommentCreateResponse;
import static com.sidenow.domain.freeboard.comment.dto.res.FreeBoardCommentResponse.FreeBoardCommentUpdateResponse;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class FreeBoardCommentServiceImpl implements FreeBoardCommentService{

    private static final String SUCCESS_LIKE_COMMENT = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_COMMENT = "좋아요 취소 완료";
    private final MemberRepository memberRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardCommentRepository freeBoardCommentRepository;
    private final FreeBoardCommentLikeRepository freeBoardCommentLikeRepository;
    private final SecurityUtils securityUtils;

    @Override
    // 자유게시판 게시글 댓글 등록
    public FreeBoardCommentCreateResponse createFreeBoardComment(Long freeBoardId, FreeBoardCommentCreateRequest req) {
        log.info("Create FreeBoard Comment Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        FreeBoard freeBoard = freeBoardRepository.findByFreeBoardId(freeBoardId).orElseThrow(FreeBoardIdNotFoundException::new);
        FreeBoardComment freeBoardComment;
        if (req.getParentId() == null) {
            freeBoardComment = createFreeBoardParentComments(req, member, freeBoard);
        } else {
            freeBoardComment = createFreeBoardChildComments(req, member, freeBoard);
        }
        freeBoardCommentRepository.save(freeBoardComment);
        log.info("Create FreeBoard Comment Service 종료");
        return FreeBoardCommentCreateResponse.from(freeBoardComment);
    }

    @Override
    // 자유게시판 게시글의 댓글 전체 조회
    public List<FreeBoardGetCommentListResponse> getFreeBoardCommentList(Long freeBoardId) {
        log.info("Read FreeBoard Comment Service Start");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());
        FreeBoard freeBoard = freeBoardRepository.findByFreeBoardId(freeBoardId).orElseThrow(FreeBoardIdNotFoundException::new);
        List<FreeBoardComment> freeBoardCommentsList = freeBoardCommentRepository.findAllByFreeBoard_FreeBoardIdOrderByCreatedAtAsc(freeBoard.getFreeBoardId());
        List<FreeBoardGetCommentListResponse> readFreeBoardCommentDto = new ArrayList<>();
        freeBoardCommentsList.forEach(s -> readFreeBoardCommentDto.add(FreeBoardGetCommentListResponse.from(s))); // 댓글 전체 조회 핵심 코드 (람다식 forEach 사용)
        log.info("Read FreeBoard Comment Service Start");
        return readFreeBoardCommentDto;
    }

    @Override
    // 자유게시판 게시글의 댓글 삭제
    public void deleteFreeBoardComment(Long freeBoardId, Long freeBoardCommentId) {
        log.info("Delete FreeBoard Comment Service 진입");

        // 게시글 존재여부 확인
        freeBoardRepository.findByFreeBoardId(freeBoardId).orElseThrow(FreeBoardIdNotFoundException::new);

        // 댓글 존재여부 확인
        FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(freeBoardCommentId).orElseThrow(NotFoundFreeBoardCommentIdException::new);

        // 댓글 작성자가 맞는지 확인
        memberRepository.findById(freeBoardComment.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        freeBoardComment.changeIsDeleted(true);
        freeBoardCommentRepository.deleteById(freeBoardComment.getCommentId()); // 댓글 삭제
        log.info("Delete FreeBoard Comment Service 종료");
    }

    @Override
    // 자유게시판 댓글 수정
    public FreeBoardCommentUpdateResponse updateFreeBoardComment(Long freeBoardId, Long freeBoardCommentId, FreeBoardCommentUpdateRequest req) {
        log.info("Update FreeBoard Comment Service 진입");

        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        // 댓글이 작성된 게시글이 맞는지 확인
        FreeBoard freeBoard = freeBoardRepository.findByFreeBoardId(freeBoardId).orElseThrow(FreeBoardIdNotFoundException::new);

        // 댓글 존재여부 확인
        FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(freeBoardCommentId).orElseThrow(NotFoundFreeBoardCommentIdException::new);

        // 댓글 작성자가 맞는지 확인
        memberRepository.findById(freeBoardComment.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        if (req.getContent() != null){
            freeBoardComment.updateContent(req.getContent());
        }

        freeBoardCommentRepository.save(freeBoardComment);
        log.info("Update FreeBoard Comment Service 종료");

        return FreeBoardCommentUpdateResponse.from(freeBoardComment);
    }

    // 자식 댓글 등록 (대댓글)
    private FreeBoardComment createFreeBoardChildComments(FreeBoardCommentCreateRequest req, Member member, FreeBoard freeBoard) {
        FreeBoardComment parent = freeBoardCommentRepository.findById(req.getParentId()).orElseThrow(NotFoundFreeBoardCommentIdException::new);

        return FreeBoardComment.builder()
                .member(member)
                .freeBoard(freeBoard)
                .isDeleted(false)
                .content(req.getContent())
                .parent(parent)
                .build();
    }

    // 부모 댓글 등록 (댓글)
    private FreeBoardComment createFreeBoardParentComments(FreeBoardCommentCreateRequest req, Member member, FreeBoard freeBoard) {

        return FreeBoardComment.builder()
                .member(member)
                .freeBoard(freeBoard)
                .isDeleted(false)
                .content(req.getContent())
                .build();
    }

    // 자유게시판 게시글 댓글 좋아요
    @Override
    @Transactional
    public String updateLikeOfFreeBoardComment(Long freeBoardId, Long freeBoardCommentId) {
        log.info("Update Like Of FreeBoardComment Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        freeBoardRepository.findById(freeBoardId).orElseThrow(FreeBoardIdNotFoundException::new);
        FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(freeBoardCommentId).orElseThrow(NotFoundFreeBoardCommentIdException::new);

        if (!hasLikeFreeBoardComment(freeBoardComment, member)){
            freeBoardComment.increaseLikes();
            return createLikeFreeBoardComment(freeBoardComment, member);
        }
        freeBoardComment.decreaseLikes();

        return removeLikeFreeBoardComment(freeBoardComment, member);
    }

    private boolean hasLikeFreeBoardComment(FreeBoardComment freeBoardComment, Member member){
        return freeBoardCommentLikeRepository.findByFreeBoardCommentAndMember(freeBoardComment, member).isPresent();
    }

    private String createLikeFreeBoardComment(FreeBoardComment freeBoardComment, Member member) {
        FreeBoardCommentLike freeBoardCommentLike = new FreeBoardCommentLike(freeBoardComment, member);
        freeBoardCommentLikeRepository.save(freeBoardCommentLike);
        log.info("댓글 좋아요 완료");

        return SUCCESS_LIKE_COMMENT;
    }

    private String removeLikeFreeBoardComment(FreeBoardComment freeBoardComment, Member member) {
        FreeBoardCommentLike freeBoardCommentLike = freeBoardCommentLikeRepository.findByFreeBoardCommentAndMember(freeBoardComment, member)
                .orElseThrow(FreeBoardCommentLikeHistoryNotFoundException::new);
        freeBoardCommentLikeRepository.delete(freeBoardCommentLike);
        log.info("댓글 좋아요 취소 완료");

        return SUCCESS_UNLIKE_COMMENT;
    }
}
