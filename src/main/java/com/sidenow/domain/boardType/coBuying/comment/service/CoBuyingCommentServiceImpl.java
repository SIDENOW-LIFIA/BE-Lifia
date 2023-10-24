package com.sidenow.domain.boardType.coBuying.comment.service;

import com.sidenow.domain.boardType.coBuying.comment.dto.req.CoBuyingCommentRequest.CoBuyingCommentCreateRequest;
import com.sidenow.domain.boardType.coBuying.comment.dto.req.CoBuyingCommentRequest.CoBuyingCommentUpdateRequest;
import com.sidenow.domain.boardType.coBuying.comment.dto.res.CoBuyingCommentResponse.CoBuyingGetCommentListResponse;
import com.sidenow.domain.boardType.coBuying.board.entity.CoBuying;
import com.sidenow.domain.boardType.coBuying.board.exception.CoBuyingIdNotFoundException;
import com.sidenow.domain.boardType.coBuying.board.repository.CoBuyingRepository;
import com.sidenow.domain.boardType.coBuying.comment.entity.CoBuyingComment;
import com.sidenow.domain.boardType.coBuying.comment.entity.CoBuyingCommentLike;
import com.sidenow.domain.boardType.coBuying.comment.exception.CoBuyingCommentIdNotFoundException;
import com.sidenow.domain.boardType.coBuying.comment.exception.CoBuyingCommentLikeHistoryNotFoundException;
import com.sidenow.domain.boardType.coBuying.comment.repository.CoBuyingCommentLikeRepository;
import com.sidenow.domain.boardType.coBuying.comment.repository.CoBuyingCommentRepository;
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

import static com.sidenow.domain.boardType.coBuying.comment.dto.res.CoBuyingCommentResponse.CoBuyingCommentCreateResponse;
import static com.sidenow.domain.boardType.coBuying.comment.dto.res.CoBuyingCommentResponse.CoBuyingCommentUpdateResponse;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class CoBuyingCommentServiceImpl implements CoBuyingCommentService {

    private static final String SUCCESS_LIKE_COMMENT = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_COMMENT = "좋아요 취소 완료";
    private final MemberRepository memberRepository;
    private final CoBuyingRepository coBuyingRepository;
    private final CoBuyingCommentRepository coBuyingCommentRepository;
    private final CoBuyingCommentLikeRepository coBuyingCommentLikeRepository;
    private final SecurityUtils securityUtils;

    @Override
    // 공구해요 게시글 댓글 등록
    public CoBuyingCommentCreateResponse createCoBuyingComment(Long coBuyingId, CoBuyingCommentCreateRequest req) {
        log.info("Create CoBuying Comment Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        CoBuying coBuying = coBuyingRepository.findByCoBuyingId(coBuyingId).orElseThrow(CoBuyingIdNotFoundException::new);
        CoBuyingComment coBuyingComment;
        if (req.getParentId() == null) {
            coBuyingComment = createCoBuyingParentComments(req, member, coBuying);
        } else {
            coBuyingComment = createCoBuyingChildComments(req, member, coBuying);
        }
        coBuyingCommentRepository.save(coBuyingComment);
        log.info("Create CoBuying Comment Service 종료");
        return CoBuyingCommentCreateResponse.from(coBuyingComment);
    }

    @Override
    // 공구해요 게시글의 댓글 전체 조회
    public List<CoBuyingGetCommentListResponse> getCoBuyingCommentList(Long coBuyingId) {
        log.info("Read CoBuying Comment Service Start");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());
        CoBuying coBuying = coBuyingRepository.findByCoBuyingId(coBuyingId).orElseThrow(CoBuyingIdNotFoundException::new);
        List<CoBuyingComment> coBuyingCommentsList = coBuyingCommentRepository.findAllByCoBuying_CoBuyingIdOrderByCreatedAtAsc(coBuying.getCoBuyingId());
        List<CoBuyingGetCommentListResponse> readCoBuyingCommentDto = new ArrayList<>();
        coBuyingCommentsList.forEach(s -> readCoBuyingCommentDto.add(CoBuyingGetCommentListResponse.from(s))); // 댓글 전체 조회 핵심 코드 (람다식 forEach 사용)
        log.info("Read CoBuying Comment Service Start");
        return readCoBuyingCommentDto;
    }

    @Override
    // 공구해요 게시글의 댓글 삭제
    public void deleteCoBuyingComment(Long coBuyingId, Long coBuyingCommentId) {
        log.info("Delete CoBuying Comment Service 진입");

        // 게시글 존재여부 확인
        coBuyingRepository.findByCoBuyingId(coBuyingId).orElseThrow(CoBuyingIdNotFoundException::new);

        // 댓글 존재여부 확인
        CoBuyingComment coBuyingComment = coBuyingCommentRepository.findById(coBuyingCommentId).orElseThrow(CoBuyingCommentIdNotFoundException::new);

        // 댓글 작성자가 맞는지 확인
        memberRepository.findById(coBuyingComment.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        coBuyingComment.changeIsDeleted(true);
        coBuyingCommentRepository.deleteById(coBuyingComment.getCommentId()); // 댓글 삭제
        log.info("Delete CoBuying Comment Service 종료");
    }

    @Override
    // 공구해요 댓글 수정
    public CoBuyingCommentUpdateResponse updateCoBuyingComment(Long coBuyingId, Long coBuyingCommentId, CoBuyingCommentUpdateRequest req) {
        log.info("Update CoBuying Comment Service 진입");

        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        // 댓글이 작성된 게시글이 맞는지 확인
        CoBuying coBuying = coBuyingRepository.findByCoBuyingId(coBuyingId).orElseThrow(CoBuyingIdNotFoundException::new);

        // 댓글 존재여부 확인
        CoBuyingComment coBuyingComment = coBuyingCommentRepository.findById(coBuyingCommentId).orElseThrow(CoBuyingCommentIdNotFoundException::new);

        // 댓글 작성자가 맞는지 확인
        memberRepository.findById(coBuyingComment.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        if (req.getContent() != null){
            coBuyingComment.updateContent(req.getContent());
        }

        coBuyingCommentRepository.save(coBuyingComment);
        log.info("Update CoBuying Comment Service 종료");

        return CoBuyingCommentUpdateResponse.from(coBuyingComment);
    }

    // 자식 댓글 등록 (대댓글)
    private CoBuyingComment createCoBuyingChildComments(CoBuyingCommentCreateRequest req, Member member, CoBuying coBuying) {
        CoBuyingComment parent = coBuyingCommentRepository.findById(req.getParentId()).orElseThrow(CoBuyingCommentIdNotFoundException::new);

        return CoBuyingComment.builder()
                .member(member)
                .coBuying(coBuying)
                .isDeleted(false)
                .content(req.getContent())
                .parent(parent)
                .build();
    }

    // 부모 댓글 등록 (댓글)
    private CoBuyingComment createCoBuyingParentComments(CoBuyingCommentCreateRequest req, Member member, CoBuying coBuying) {

        return CoBuyingComment.builder()
                .member(member)
                .coBuying(coBuying)
                .isDeleted(false)
                .content(req.getContent())
                .build();
    }

    // 공구해요 게시글 댓글 좋아요
    @Override
    @Transactional
    public String updateLikeOfCoBuyingComment(Long coBuyingId, Long coBuyingCommentId) {
        log.info("Update Like Of CoBuyingComment Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        coBuyingRepository.findById(coBuyingId).orElseThrow(CoBuyingIdNotFoundException::new);
        CoBuyingComment coBuyingComment = coBuyingCommentRepository.findById(coBuyingCommentId).orElseThrow(CoBuyingCommentIdNotFoundException::new);

        if (!hasLikeCoBuyingComment(coBuyingComment, member)){
            coBuyingComment.increaseLikes();
            return createLikeCoBuyingComment(coBuyingComment, member);
        }
        coBuyingComment.decreaseLikes();

        return removeLikeCoBuyingComment(coBuyingComment, member);
    }

    private boolean hasLikeCoBuyingComment(CoBuyingComment coBuyingComment, Member member){
        return coBuyingCommentLikeRepository.findByCoBuyingCommentAndMember(coBuyingComment, member).isPresent();
    }

    private String createLikeCoBuyingComment(CoBuyingComment coBuyingComment, Member member) {
        CoBuyingCommentLike coBuyingCommentLike = new CoBuyingCommentLike(coBuyingComment, member);
        coBuyingCommentLikeRepository.save(coBuyingCommentLike);
        log.info("댓글 좋아요 완료");

        return SUCCESS_LIKE_COMMENT;
    }

    private String removeLikeCoBuyingComment(CoBuyingComment coBuyingComment, Member member) {
        CoBuyingCommentLike coBuyingCommentLike = coBuyingCommentLikeRepository.findByCoBuyingCommentAndMember(coBuyingComment, member)
                .orElseThrow(CoBuyingCommentLikeHistoryNotFoundException::new);
        coBuyingCommentLikeRepository.delete(coBuyingCommentLike);
        log.info("댓글 좋아요 취소 완료");

        return SUCCESS_UNLIKE_COMMENT;
    }
}
