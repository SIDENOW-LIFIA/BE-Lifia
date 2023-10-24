package com.sidenow.domain.boardType.childcare.comment.service;

import com.sidenow.domain.boardType.childcare.board.entity.Childcare;
import com.sidenow.domain.boardType.childcare.board.exception.ChildcareIdNotFoundException;
import com.sidenow.domain.boardType.childcare.board.repository.ChildcareRepository;
import com.sidenow.domain.boardType.childcare.comment.entity.ChildcareComment;
import com.sidenow.domain.boardType.childcare.comment.entity.ChildcareCommentLike;
import com.sidenow.domain.boardType.childcare.comment.exception.ChildcareCommentLikeHistoryNotFoundException;
import com.sidenow.domain.boardType.childcare.comment.exception.ChildcareCommentIdNotFoundException;
import com.sidenow.domain.boardType.childcare.comment.repository.ChildcareCommentLikeRepository;
import com.sidenow.domain.boardType.childcare.comment.repository.ChildcareCommentRepository;
import com.sidenow.domain.boardType.childcare.comment.dto.req.ChildcareCommentRequest.ChildcareCommentCreateRequest;
import com.sidenow.domain.boardType.childcare.comment.dto.req.ChildcareCommentRequest.ChildcareCommentUpdateRequest;
import com.sidenow.domain.boardType.childcare.comment.dto.res.ChildcareCommentResponse.ChildcareGetCommentListResponse;
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

import static com.sidenow.domain.boardType.childcare.comment.dto.res.ChildcareCommentResponse.ChildcareCommentCreateResponse;
import static com.sidenow.domain.boardType.childcare.comment.dto.res.ChildcareCommentResponse.ChildcareCommentUpdateResponse;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class ChildcareCommentServiceImpl implements ChildcareCommentService {

    private static final String SUCCESS_LIKE_COMMENT = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_COMMENT = "좋아요 취소 완료";
    private final MemberRepository memberRepository;
    private final ChildcareRepository childcareRepository;
    private final ChildcareCommentRepository childcareCommentRepository;
    private final ChildcareCommentLikeRepository childcareCommentLikeRepository;
    private final SecurityUtils securityUtils;

    @Override
    // 육아해요 게시글 댓글 등록
    public ChildcareCommentCreateResponse createChildcareComment(Long childcareId, ChildcareCommentCreateRequest req) {
        log.info("Create Childcare Comment Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        Childcare childcare = childcareRepository.findByChildcareId(childcareId).orElseThrow(ChildcareIdNotFoundException::new);
        ChildcareComment childcareComment;
        if (req.getParentId() == null) {
            childcareComment = createChildcareParentComments(req, member, childcare);
        } else {
            childcareComment = createChildcareChildComments(req, member, childcare);
        }
        childcareCommentRepository.save(childcareComment);
        log.info("Create Childcare Comment Service 종료");
        return ChildcareCommentCreateResponse.from(childcareComment);
    }

    @Override
    // 육아해요 게시글의 댓글 전체 조회
    public List<ChildcareGetCommentListResponse> getChildcareCommentList(Long childcareId) {
        log.info("Read Childcare Comment Service Start");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());
        Childcare childcare = childcareRepository.findByChildcareId(childcareId).orElseThrow(ChildcareIdNotFoundException::new);
        List<ChildcareComment> childcareCommentsList = childcareCommentRepository.findAllByChildcare_ChildcareIdOrderByCreatedAtAsc(childcare.getChildcareId());
        List<ChildcareGetCommentListResponse> readChildcareCommentDto = new ArrayList<>();
        childcareCommentsList.forEach(s -> readChildcareCommentDto.add(ChildcareGetCommentListResponse.from(s))); // 댓글 전체 조회 핵심 코드 (람다식 forEach 사용)
        log.info("Read Childcare Comment Service Start");
        return readChildcareCommentDto;
    }

    @Override
    // 육아해요 게시글의 댓글 삭제
    public void deleteChildcareComment(Long childcareId, Long childcareCommentId) {
        log.info("Delete Childcare Comment Service 진입");

        // 게시글 존재여부 확인
        childcareRepository.findByChildcareId(childcareId).orElseThrow(ChildcareIdNotFoundException::new);

        // 댓글 존재여부 확인
        ChildcareComment childcareComment = childcareCommentRepository.findById(childcareCommentId).orElseThrow(ChildcareCommentIdNotFoundException::new);

        // 댓글 작성자가 맞는지 확인
        memberRepository.findById(childcareComment.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        childcareComment.changeIsDeleted(true);
        childcareCommentRepository.deleteById(childcareComment.getCommentId()); // 댓글 삭제
        log.info("Delete Childcare Comment Service 종료");
    }

    @Override
    // 육아해요 댓글 수정
    public ChildcareCommentUpdateResponse updateChildcareComment(Long childcareId, Long childcareCommentId, ChildcareCommentUpdateRequest req) {
        log.info("Update Childcare Comment Service 진입");

        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        // 댓글이 작성된 게시글이 맞는지 확인
        Childcare childcare = childcareRepository.findByChildcareId(childcareId).orElseThrow(ChildcareIdNotFoundException::new);

        // 댓글 존재여부 확인
        ChildcareComment childcareComment = childcareCommentRepository.findById(childcareCommentId).orElseThrow(ChildcareCommentIdNotFoundException::new);

        // 댓글 작성자가 맞는지 확인
        memberRepository.findById(childcareComment.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        if (req.getContent() != null){
            childcareComment.updateContent(req.getContent());
        }

        childcareCommentRepository.save(childcareComment);
        log.info("Update Childcare Comment Service 종료");

        return ChildcareCommentUpdateResponse.from(childcareComment);
    }

    // 자식 댓글 등록 (대댓글)
    private ChildcareComment createChildcareChildComments(ChildcareCommentCreateRequest req, Member member, Childcare childcare) {
        ChildcareComment parent = childcareCommentRepository.findById(req.getParentId()).orElseThrow(ChildcareCommentIdNotFoundException::new);

        return ChildcareComment.builder()
                .member(member)
                .childcare(childcare)
                .isDeleted(false)
                .content(req.getContent())
                .parent(parent)
                .build();
    }

    // 부모 댓글 등록 (댓글)
    private ChildcareComment createChildcareParentComments(ChildcareCommentCreateRequest req, Member member, Childcare childcare) {

        return ChildcareComment.builder()
                .member(member)
                .childcare(childcare)
                .isDeleted(false)
                .content(req.getContent())
                .build();
    }

    // 육아해요 게시글 댓글 좋아요
    @Override
    @Transactional
    public String updateLikeOfChildcareComment(Long childcareId, Long childcareCommentId) {
        log.info("Update Like Of ChildcareComment Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        childcareRepository.findById(childcareId).orElseThrow(ChildcareIdNotFoundException::new);
        ChildcareComment childcareComment = childcareCommentRepository.findById(childcareCommentId).orElseThrow(ChildcareCommentIdNotFoundException::new);

        if (!hasLikeChildcareComment(childcareComment, member)){
            childcareComment.increaseLikes();
            return createLikeChildcareComment(childcareComment, member);
        }
        childcareComment.decreaseLikes();

        return removeLikeChildcareComment(childcareComment, member);
    }

    private boolean hasLikeChildcareComment(ChildcareComment childcareComment, Member member){
        return childcareCommentLikeRepository.findByChildcareCommentAndMember(childcareComment, member).isPresent();
    }

    private String createLikeChildcareComment(ChildcareComment childcareComment, Member member) {
        ChildcareCommentLike childcareCommentLike = new ChildcareCommentLike(childcareComment, member);
        childcareCommentLikeRepository.save(childcareCommentLike);
        log.info("댓글 좋아요 완료");

        return SUCCESS_LIKE_COMMENT;
    }

    private String removeLikeChildcareComment(ChildcareComment childcareComment, Member member) {
        ChildcareCommentLike childcareCommentLike = childcareCommentLikeRepository.findByChildcareCommentAndMember(childcareComment, member)
                .orElseThrow(ChildcareCommentLikeHistoryNotFoundException::new);
        childcareCommentLikeRepository.delete(childcareCommentLike);
        log.info("댓글 좋아요 취소 완료");

        return SUCCESS_UNLIKE_COMMENT;
    }
}
