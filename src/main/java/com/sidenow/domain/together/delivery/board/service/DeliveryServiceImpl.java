package com.sidenow.domain.together.delivery.board.service;

import com.sidenow.domain.together.delivery.board.dto.req.DeliveryRequest.DeliveryCreateRequest;
import com.sidenow.domain.together.delivery.board.dto.req.DeliveryRequest.DeliveryUpdateRequest;
import com.sidenow.domain.together.delivery.board.dto.res.DeliveryResponse.*;
import com.sidenow.domain.together.delivery.board.entity.Delivery;
import com.sidenow.domain.together.delivery.board.entity.DeliveryLike;
import com.sidenow.domain.together.delivery.board.exception.DeliveryIdNotFoundException;
import com.sidenow.domain.together.delivery.board.exception.DeliveryLikeHistoryNotFoundException;
import com.sidenow.domain.together.delivery.board.repository.DeliveryLikeRepository;
import com.sidenow.domain.together.delivery.board.repository.DeliveryRepository;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberNotExistException;
import com.sidenow.domain.member.exception.MemberNotLoginException;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.aws.service.AwsS3Service;
import com.sidenow.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private static final String SUCCESS_LIKE_BOARD = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_BOARD = "좋아요 취소 완료";
    private static final int DELIVERY_PAGE_SIZE = 10;

    private final MemberRepository memberRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryLikeRepository deliveryLikeRepository;
    private final AwsS3Service awsS3Service;
    private final SecurityUtils securityUtils;

    // 배달해요 게시글 등록
    @Override
    @Transactional
    public DeliveryCreateResponse createDelivery(DeliveryCreateRequest req, MultipartFile image) {
        log.info("Create Delivery Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();

        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        String imgUrl = "";
        if (image != null){
            imgUrl = awsS3Service.uploadFile(image);
            log.info("업로드 된 파일: "+imgUrl);
        }

        Delivery delivery = DeliveryCreateRequest.to(req, member, imgUrl);
        deliveryRepository.save(delivery);

        log.info("Create Delivery Service 종료");

        return DeliveryCreateResponse.from(delivery);
    }

    // 배달해요 게시글 단건 조회
    @Override
    @Transactional
    public DeliveryGetResponse getDelivery(Long id){
        log.info("Get Delivery Service 진입");
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(DeliveryIdNotFoundException::new);
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId());

        delivery.increaseHits();

        DeliveryGetResponse deliveryGetResponse = DeliveryGetResponse.from(delivery);
        log.info("Get Delivery Post Service End");
        return deliveryGetResponse;
    }

    // 배달해요 게시글 전체 조회
    @Override
    @Transactional
    public AllDeliverys getDeliveryList(Integer page){

        log.info("Get Delivery Post List Service 진입");
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId());
        if (page == null) {
            page = 1;
        }

        Pageable pageable = PageRequest.of(page-1, DELIVERY_PAGE_SIZE);

        Page<Delivery> deliveryPage = deliveryRepository.findByOrderByCreatedAtDesc(pageable);
        List<DeliveryGetListResponse> deliveryGetPostList = new ArrayList<>();
        for (int i = 0; i < deliveryPage.getContent().size(); i++) {
            deliveryGetPostList.add(DeliveryGetListResponse.from(deliveryPage.getContent().get(i)));
        }
        log.info("Get Delivery Post List Service 종료");

        return AllDeliverys.builder()
                        .deliverys(deliveryGetPostList)
                        .build();
    }

    // 배달해요 게시글 수정
    @Override
    @Transactional
    public DeliveryUpdateResponse updateDelivery(Long id, DeliveryUpdateRequest req, MultipartFile image){
        log.info("Update Delivery Service 진입");

        // 게시글 존재여부 확인
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(DeliveryIdNotFoundException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(delivery.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        if (image != null){
            // 기존 이미지 파일 삭제
            awsS3Service.deleteFile(image.getOriginalFilename());
            String imgUrl = awsS3Service.uploadFile(image);
            log.info("업로드된 파일: "+imgUrl);
            delivery.updateImage(imgUrl);
        }

        if (req.getTitle() != null){
            delivery.updateTitle(req.getTitle());
        }

        if (req.getContent() != null) {
            delivery.updateContent(req.getContent());
        }

        deliveryRepository.save(delivery);

        log.info("Update Delivery Service 종료");

        return DeliveryUpdateResponse.from(delivery);
    }

    // 배달해요 게시글 삭제
    @Override
    @Transactional
    public void deleteDelivery(Long id){
        log.info("Delete Delivery Post Service 진입");

        // 게시글 존재여부 확인
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(DeliveryIdNotFoundException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(delivery.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        deliveryRepository.delete(delivery);

        log.info("Delete Delivery Post Service 종료");
    }

    // 배달해요 게시글 좋아요
    @Override
    @Transactional
    public String updateLikeOfDelivery(Long id) {
        log.info("Update Like Of Delivery Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(DeliveryIdNotFoundException::new);
        
        if (!hasLikeDelivery(delivery, member)){
            delivery.increaseLikes();
            return createLikeDelivery(delivery, member);
        }

        delivery.decreaseLikes();
        return removeLikeDelivery(delivery, member);
    }
    
    private boolean hasLikeDelivery(Delivery delivery, Member member){
        return deliveryLikeRepository.findByDeliveryAndMember(delivery, member).isPresent();
    }

    private String createLikeDelivery(Delivery delivery, Member member) {
        DeliveryLike deliveryLike = new DeliveryLike(delivery, member);
        deliveryLikeRepository.save(deliveryLike);
        log.info("게시글 좋아요 완료");
        return SUCCESS_LIKE_BOARD;
    }

    private String removeLikeDelivery(Delivery delivery, Member member) {
        DeliveryLike deliveryLike = deliveryLikeRepository.findByDeliveryAndMember(delivery, member)
                        .orElseThrow(DeliveryLikeHistoryNotFoundException::new);
        deliveryLikeRepository.delete(deliveryLike);
        log.info("게시글 좋아요 취소 완료");

        return SUCCESS_UNLIKE_BOARD;
    }
}
