package com.sidenow.domain.together.delivery.comment.service;

import com.sidenow.domain.together.delivery.comment.dto.req.DeliveryCommentRequest.DeliveryCommentCreateRequest;
import com.sidenow.domain.together.delivery.comment.dto.req.DeliveryCommentRequest.DeliveryCommentUpdateRequest;
import com.sidenow.domain.together.delivery.comment.dto.res.DeliveryCommentResponse.DeliveryGetCommentListResponse;
import com.sidenow.domain.together.delivery.board.entity.Delivery;
import com.sidenow.domain.together.delivery.board.exception.DeliveryIdNotFoundException;
import com.sidenow.domain.together.delivery.board.repository.DeliveryRepository;
import com.sidenow.domain.together.delivery.comment.entity.DeliveryComment;
import com.sidenow.domain.together.delivery.comment.entity.DeliveryCommentLike;
import com.sidenow.domain.together.delivery.comment.exception.DeliveryCommentLikeHistoryNotFoundException;
import com.sidenow.domain.together.delivery.comment.exception.DeliveryCommentIdNotFoundException;
import com.sidenow.domain.together.delivery.comment.repository.DeliveryCommentLikeRepository;
import com.sidenow.domain.together.delivery.comment.repository.DeliveryCommentRepository;
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

import static com.sidenow.domain.together.delivery.comment.dto.res.DeliveryCommentResponse.DeliveryCommentCreateResponse;
import static com.sidenow.domain.together.delivery.comment.dto.res.DeliveryCommentResponse.DeliveryCommentUpdateResponse;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class DeliveryCommentServiceImpl implements DeliveryCommentService {

    private static final String SUCCESS_LIKE_COMMENT = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_COMMENT = "좋아요 취소 완료";
    private final MemberRepository memberRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryCommentRepository deliveryCommentRepository;
    private final DeliveryCommentLikeRepository deliveryCommentLikeRepository;
    private final SecurityUtils securityUtils;

    @Override
    // 배달해요 게시글 댓글 등록
    public DeliveryCommentCreateResponse createDeliveryComment(Long deliveryId, DeliveryCommentCreateRequest req) {
        log.info("Create Delivery Comment Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        Delivery delivery = deliveryRepository.findByDeliveryId(deliveryId).orElseThrow(DeliveryIdNotFoundException::new);
        DeliveryComment deliveryComment;
        if (req.getParentId() == null) {
            deliveryComment = createDeliveryParentComments(req, member, delivery);
        } else {
            deliveryComment = createDeliveryChildComments(req, member, delivery);
        }
        deliveryCommentRepository.save(deliveryComment);
        log.info("Create Delivery Comment Service 종료");
        return DeliveryCommentCreateResponse.from(deliveryComment);
    }

    @Override
    // 배달해요 게시글의 댓글 전체 조회
    public List<DeliveryGetCommentListResponse> getDeliveryCommentList(Long deliveryId) {
        log.info("Read Delivery Comment Service Start");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());
        Delivery delivery = deliveryRepository.findByDeliveryId(deliveryId).orElseThrow(DeliveryIdNotFoundException::new);
        List<DeliveryComment> deliveryCommentsList = deliveryCommentRepository.findAllByDelivery_DeliveryIdOrderByCreatedAtAsc(delivery.getDeliveryId());
        List<DeliveryGetCommentListResponse> readDeliveryCommentDto = new ArrayList<>();
        deliveryCommentsList.forEach(s -> readDeliveryCommentDto.add(DeliveryGetCommentListResponse.from(s))); // 댓글 전체 조회 핵심 코드 (람다식 forEach 사용)
        log.info("Read Delivery Comment Service Start");
        return readDeliveryCommentDto;
    }

    @Override
    // 배달해요 게시글의 댓글 삭제
    public void deleteDeliveryComment(Long deliveryId, Long deliveryCommentId) {
        log.info("Delete Delivery Comment Service 진입");

        // 게시글 존재여부 확인
        deliveryRepository.findByDeliveryId(deliveryId).orElseThrow(DeliveryIdNotFoundException::new);

        // 댓글 존재여부 확인
        DeliveryComment deliveryComment = deliveryCommentRepository.findById(deliveryCommentId).orElseThrow(DeliveryCommentIdNotFoundException::new);

        // 댓글 작성자가 맞는지 확인
        memberRepository.findById(deliveryComment.getMember().getId()).orElseThrow(MemberNotExistException::new);

        deliveryComment.changeIsDeleted(true);
        deliveryCommentRepository.deleteById(deliveryComment.getCommentId()); // 댓글 삭제
        log.info("Delete Delivery Comment Service 종료");
    }

    @Override
    // 배달해요 댓글 수정
    public DeliveryCommentUpdateResponse updateDeliveryComment(Long deliveryId, Long deliveryCommentId, DeliveryCommentUpdateRequest req) {
        log.info("Update Delivery Comment Service 진입");

        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        // 댓글이 작성된 게시글이 맞는지 확인
        Delivery delivery = deliveryRepository.findByDeliveryId(deliveryId).orElseThrow(DeliveryIdNotFoundException::new);

        // 댓글 존재여부 확인
        DeliveryComment deliveryComment = deliveryCommentRepository.findById(deliveryCommentId).orElseThrow(DeliveryCommentIdNotFoundException::new);

        // 댓글 작성자가 맞는지 확인
        memberRepository.findById(deliveryComment.getMember().getId()).orElseThrow(MemberNotExistException::new);

        if (req.getContent() != null){
            deliveryComment.updateContent(req.getContent());
        }

        deliveryCommentRepository.save(deliveryComment);
        log.info("Update Delivery Comment Service 종료");

        return DeliveryCommentUpdateResponse.from(deliveryComment);
    }

    // 자식 댓글 등록 (대댓글)
    private DeliveryComment createDeliveryChildComments(DeliveryCommentCreateRequest req, Member member, Delivery delivery) {
        DeliveryComment parent = deliveryCommentRepository.findById(req.getParentId()).orElseThrow(DeliveryCommentIdNotFoundException::new);

        return DeliveryComment.builder()
                .member(member)
                .delivery(delivery)
                .isDeleted(false)
                .content(req.getContent())
                .parent(parent)
                .build();
    }

    // 부모 댓글 등록 (댓글)
    private DeliveryComment createDeliveryParentComments(DeliveryCommentCreateRequest req, Member member, Delivery delivery) {

        return DeliveryComment.builder()
                .member(member)
                .delivery(delivery)
                .isDeleted(false)
                .content(req.getContent())
                .build();
    }

    // 배달해요 게시글 댓글 좋아요
    @Override
    @Transactional
    public String updateLikeOfDeliveryComment(Long deliveryId, Long deliveryCommentId) {
        log.info("Update Like Of DeliveryComment Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getId()).get();
        deliveryRepository.findById(deliveryId).orElseThrow(DeliveryIdNotFoundException::new);
        DeliveryComment deliveryComment = deliveryCommentRepository.findById(deliveryCommentId).orElseThrow(DeliveryCommentIdNotFoundException::new);

        if (!hasLikeDeliveryComment(deliveryComment, member)){
            deliveryComment.increaseLikes();
            return createLikeDeliveryComment(deliveryComment, member);
        }
        deliveryComment.decreaseLikes();

        return removeLikeDeliveryComment(deliveryComment, member);
    }

    private boolean hasLikeDeliveryComment(DeliveryComment deliveryComment, Member member){
        return deliveryCommentLikeRepository.findByDeliveryCommentAndMember(deliveryComment, member).isPresent();
    }

    private String createLikeDeliveryComment(DeliveryComment deliveryComment, Member member) {
        DeliveryCommentLike deliveryCommentLike = new DeliveryCommentLike(deliveryComment, member);
        deliveryCommentLikeRepository.save(deliveryCommentLike);
        log.info("댓글 좋아요 완료");

        return SUCCESS_LIKE_COMMENT;
    }

    private String removeLikeDeliveryComment(DeliveryComment deliveryComment, Member member) {
        DeliveryCommentLike deliveryCommentLike = deliveryCommentLikeRepository.findByDeliveryCommentAndMember(deliveryComment, member)
                .orElseThrow(DeliveryCommentLikeHistoryNotFoundException::new);
        deliveryCommentLikeRepository.delete(deliveryCommentLike);
        log.info("댓글 좋아요 취소 완료");

        return SUCCESS_UNLIKE_COMMENT;
    }
}
