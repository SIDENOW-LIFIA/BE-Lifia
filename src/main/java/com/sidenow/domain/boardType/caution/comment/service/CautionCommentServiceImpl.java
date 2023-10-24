package com.sidenow.domain.boardType.caution.comment.service;

import com.sidenow.domain.boardType.caution.board.entity.Caution;
import com.sidenow.domain.boardType.caution.board.exception.CautionIdNotFoundException;
import com.sidenow.domain.boardType.caution.board.repository.CautionRepository;
import com.sidenow.domain.boardType.caution.comment.entity.CautionComment;
import com.sidenow.domain.boardType.caution.comment.entity.CautionCommentLike;
import com.sidenow.domain.boardType.caution.comment.exception.CautionCommentIdNotFoundException;
import com.sidenow.domain.boardType.caution.comment.exception.CautionCommentLikeHistoryNotFoundException;
import com.sidenow.domain.boardType.caution.comment.repository.CautionCommentLikeRepository;
import com.sidenow.domain.boardType.caution.comment.repository.CautionCommentRepository;
import com.sidenow.domain.boardType.caution.comment.dto.req.CautionCommentRequest.CautionCommentCreateRequest;
import com.sidenow.domain.boardType.caution.comment.dto.req.CautionCommentRequest.CautionCommentUpdateRequest;
import com.sidenow.domain.boardType.caution.comment.dto.res.CautionCommentResponse.CautionGetCommentListResponse;
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

import static com.sidenow.domain.boardType.caution.comment.dto.res.CautionCommentResponse.CautionCommentCreateResponse;
import static com.sidenow.domain.boardType.caution.comment.dto.res.CautionCommentResponse.CautionCommentUpdateResponse;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class CautionCommentServiceImpl implements CautionCommentService {

    private static final String SUCCESS_LIKE_COMMENT = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_COMMENT = "좋아요 취소 완료";
    private final MemberRepository memberRepository;
    private final CautionRepository cautionRepository;
    private final CautionCommentRepository cautionCommentRepository;
    private final CautionCommentLikeRepository cautionCommentLikeRepository;
    private final SecurityUtils securityUtils;

    @Override
    // 조심해요 게시글 댓글 등록
    public CautionCommentCreateResponse createCautionComment(Long cautionId, CautionCommentCreateRequest req) {
        log.info("Create Caution Comment Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        Caution caution = cautionRepository.findByCautionId(cautionId).orElseThrow(CautionIdNotFoundException::new);
        CautionComment cautionComment;
        if (req.getParentId() == null) {
            cautionComment = createCautionParentComments(req, member, caution);
        } else {
            cautionComment = createCautionChildComments(req, member, caution);
        }
        cautionCommentRepository.save(cautionComment);
        log.info("Create Caution Comment Service 종료");
        return CautionCommentCreateResponse.from(cautionComment);
    }

    @Override
    // 조심해요 게시글의 댓글 전체 조회
    public List<CautionGetCommentListResponse> getCautionCommentList(Long cautionId) {
        log.info("Read Caution Comment Service Start");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());
        Caution caution = cautionRepository.findByCautionId(cautionId).orElseThrow(CautionIdNotFoundException::new);
        List<CautionComment> cautionCommentsList = cautionCommentRepository.findAllByCaution_CautionIdOrderByCreatedAtAsc(caution.getCautionId());
        List<CautionGetCommentListResponse> readCautionCommentDto = new ArrayList<>();
        cautionCommentsList.forEach(s -> readCautionCommentDto.add(CautionGetCommentListResponse.from(s))); // 댓글 전체 조회 핵심 코드 (람다식 forEach 사용)
        log.info("Read Caution Comment Service Start");
        return readCautionCommentDto;
    }

    @Override
    // 조심해요 게시글의 댓글 삭제
    public void deleteCautionComment(Long cautionId, Long cautionCommentId) {
        log.info("Delete Caution Comment Service 진입");

        // 게시글 존재여부 확인
        cautionRepository.findByCautionId(cautionId).orElseThrow(CautionIdNotFoundException::new);

        // 댓글 존재여부 확인
        CautionComment cautionComment = cautionCommentRepository.findById(cautionCommentId).orElseThrow(CautionCommentIdNotFoundException::new);

        // 댓글 작성자가 맞는지 확인
        memberRepository.findById(cautionComment.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        cautionComment.changeIsDeleted(true);
        cautionCommentRepository.deleteById(cautionComment.getCommentId()); // 댓글 삭제
        log.info("Delete Caution Comment Service 종료");
    }

    @Override
    // 조심해요 댓글 수정
    public CautionCommentUpdateResponse updateCautionComment(Long cautionId, Long cautionCommentId, CautionCommentUpdateRequest req) {
        log.info("Update Caution Comment Service 진입");

        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        // 댓글이 작성된 게시글이 맞는지 확인
        Caution caution = cautionRepository.findByCautionId(cautionId).orElseThrow(CautionIdNotFoundException::new);

        // 댓글 존재여부 확인
        CautionComment cautionComment = cautionCommentRepository.findById(cautionCommentId).orElseThrow(CautionCommentIdNotFoundException::new);

        // 댓글 작성자가 맞는지 확인
        memberRepository.findById(cautionComment.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        if (req.getContent() != null){
            cautionComment.updateContent(req.getContent());
        }

        cautionCommentRepository.save(cautionComment);
        log.info("Update Caution Comment Service 종료");

        return CautionCommentUpdateResponse.from(cautionComment);
    }

    // 자식 댓글 등록 (대댓글)
    private CautionComment createCautionChildComments(CautionCommentCreateRequest req, Member member, Caution caution) {
        CautionComment parent = cautionCommentRepository.findById(req.getParentId()).orElseThrow(CautionCommentIdNotFoundException::new);

        return CautionComment.builder()
                .member(member)
                .caution(caution)
                .isDeleted(false)
                .content(req.getContent())
                .parent(parent)
                .build();
    }

    // 부모 댓글 등록 (댓글)
    private CautionComment createCautionParentComments(CautionCommentCreateRequest req, Member member, Caution caution) {

        return CautionComment.builder()
                .member(member)
                .caution(caution)
                .isDeleted(false)
                .content(req.getContent())
                .build();
    }

    // 조심해요 게시글 댓글 좋아요
    @Override
    @Transactional
    public String updateLikeOfCautionComment(Long cautionId, Long cautionCommentId) {
        log.info("Update Like Of CautionComment Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        cautionRepository.findById(cautionId).orElseThrow(CautionIdNotFoundException::new);
        CautionComment cautionComment = cautionCommentRepository.findById(cautionCommentId).orElseThrow(CautionCommentIdNotFoundException::new);

        if (!hasLikeCautionComment(cautionComment, member)){
            cautionComment.increaseLikes();
            return createLikeCautionComment(cautionComment, member);
        }
        cautionComment.decreaseLikes();

        return removeLikeCautionComment(cautionComment, member);
    }

    private boolean hasLikeCautionComment(CautionComment cautionComment, Member member){
        return cautionCommentLikeRepository.findByCautionCommentAndMember(cautionComment, member).isPresent();
    }

    private String createLikeCautionComment(CautionComment cautionComment, Member member) {
        CautionCommentLike cautionCommentLike = new CautionCommentLike(cautionComment, member);
        cautionCommentLikeRepository.save(cautionCommentLike);
        log.info("댓글 좋아요 완료");

        return SUCCESS_LIKE_COMMENT;
    }

    private String removeLikeCautionComment(CautionComment cautionComment, Member member) {
        CautionCommentLike cautionCommentLike = cautionCommentLikeRepository.findByCautionCommentAndMember(cautionComment, member)
                .orElseThrow(CautionCommentLikeHistoryNotFoundException::new);
        cautionCommentLikeRepository.delete(cautionCommentLike);
        log.info("댓글 좋아요 취소 완료");

        return SUCCESS_UNLIKE_COMMENT;
    }
}
