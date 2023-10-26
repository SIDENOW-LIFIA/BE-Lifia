package com.sidenow.domain.together.childcare.board.service;

import com.sidenow.domain.together.childcare.board.entity.Childcare;
import com.sidenow.domain.together.childcare.board.entity.ChildcareLike;
import com.sidenow.domain.together.childcare.board.exception.ChildcareIdNotFoundException;
import com.sidenow.domain.together.childcare.board.exception.ChildcareLikeHistoryNotFoundException;
import com.sidenow.domain.together.childcare.board.repository.ChildcareLikeRepository;
import com.sidenow.domain.together.childcare.board.repository.ChildcareRepository;
import com.sidenow.domain.together.childcare.board.dto.req.ChildcareRequest.ChildcareCreateRequest;
import com.sidenow.domain.together.childcare.board.dto.req.ChildcareRequest.ChildcareUpdateRequest;
import com.sidenow.domain.together.childcare.board.dto.res.ChildcareResponse.*;
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
public class ChildcareServiceImpl implements ChildcareService {

    private static final String SUCCESS_LIKE_BOARD = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_BOARD = "좋아요 취소 완료";
    private static final int CHILDCARE_PAGE_SIZE = 10;

    private final MemberRepository memberRepository;
    private final ChildcareRepository childcareRepository;
    private final ChildcareLikeRepository childcareLikeRepository;
    private final AwsS3Service awsS3Service;
    private final SecurityUtils securityUtils;

    // 육아해요 게시글 등록
    @Override
    @Transactional
    public ChildcareCreateResponse createChildcare(ChildcareCreateRequest req, MultipartFile image) {
        log.info("Create Childcare Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getId()).get();

        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        String imgUrl = "";
        if (image != null){
            imgUrl = awsS3Service.uploadFile(image);
            log.info("업로드 된 파일: "+imgUrl);
        }

        Childcare childcare = ChildcareCreateRequest.to(req, member, imgUrl);
        childcareRepository.save(childcare);

        log.info("Create Childcare Service 종료");

        return ChildcareCreateResponse.from(childcare);
    }

    // 육아해요 게시글 단건 조회
    @Override
    @Transactional
    public ChildcareGetResponse getChildcare(Long id){
        log.info("Get Childcare Service 진입");
        Childcare childcare = childcareRepository.findById(id).orElseThrow(ChildcareIdNotFoundException::new);
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getId());

        childcare.increaseHits();

        ChildcareGetResponse childcareGetResponse = ChildcareGetResponse.from(childcare);
        log.info("Get Childcare Post Service End");
        return childcareGetResponse;
    }

    // 육아해요 게시글 전체 조회
    @Override
    @Transactional
    public AllChildcares getChildcareList(Integer page){

        log.info("Get Childcare Post List Service 진입");
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getId());
        if (page == null) {
            page = 1;
        }

        Pageable pageable = PageRequest.of(page-1, CHILDCARE_PAGE_SIZE);

        Page<Childcare> childcarePage = childcareRepository.findByOrderByCreatedAtDesc(pageable);
        List<ChildcareGetListResponse> childcareGetPostList = new ArrayList<>();
        for (int i = 0; i < childcarePage.getContent().size(); i++) {
            childcareGetPostList.add(ChildcareGetListResponse.from(childcarePage.getContent().get(i)));
        }
        log.info("Get Childcare Post List Service 종료");

        return AllChildcares.builder()
                        .childcares(childcareGetPostList)
                        .build();
    }

    // 육아해요 게시글 수정
    @Override
    @Transactional
    public ChildcareUpdateResponse updateChildcare(Long id, ChildcareUpdateRequest req, MultipartFile image){
        log.info("Update Childcare Service 진입");

        // 게시글 존재여부 확인
        Childcare childcare = childcareRepository.findById(id)
                .orElseThrow(ChildcareIdNotFoundException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(childcare.getMember().getId()).orElseThrow(MemberNotExistException::new);

        if (image != null){
            // 기존 이미지 파일 삭제
            awsS3Service.deleteFile(image.getOriginalFilename());
            String imgUrl = awsS3Service.uploadFile(image);
            log.info("업로드된 파일: "+imgUrl);
            childcare.updateImage(imgUrl);
        }

        if (req.getTitle() != null){
            childcare.updateTitle(req.getTitle());
        }

        if (req.getContent() != null) {
            childcare.updateContent(req.getContent());
        }

        childcareRepository.save(childcare);

        log.info("Update Childcare Service 종료");

        return ChildcareUpdateResponse.from(childcare);
    }

    // 육아해요 게시글 삭제
    @Override
    @Transactional
    public void deleteChildcare(Long id){
        log.info("Delete Childcare Post Service 진입");

        // 게시글 존재여부 확인
        Childcare childcare = childcareRepository.findById(id).orElseThrow(ChildcareIdNotFoundException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(childcare.getMember().getId()).orElseThrow(MemberNotExistException::new);

        childcareRepository.delete(childcare);

        log.info("Delete Childcare Post Service 종료");
    }

    // 육아해요 게시글 좋아요
    @Override
    @Transactional
    public String updateLikeOfChildcare(Long id) {
        log.info("Update Like Of Childcare Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getId()).get();
        Childcare childcare = childcareRepository.findById(id).orElseThrow(ChildcareIdNotFoundException::new);
        
        if (!hasLikeChildcare(childcare, member)){
            childcare.increaseLikes();
            return createLikeChildcare(childcare, member);
        }

        childcare.decreaseLikes();
        return removeLikeChildcare(childcare, member);
    }
    
    private boolean hasLikeChildcare(Childcare childcare, Member member){
        return childcareLikeRepository.findByChildcareAndMember(childcare, member).isPresent();
    }

    private String createLikeChildcare(Childcare childcare, Member member) {
        ChildcareLike childcareLike = new ChildcareLike(childcare, member);
        childcareLikeRepository.save(childcareLike);
        log.info("게시글 좋아요 완료");
        return SUCCESS_LIKE_BOARD;
    }

    private String removeLikeChildcare(Childcare childcare, Member member) {
        ChildcareLike childcareLike = childcareLikeRepository.findByChildcareAndMember(childcare, member)
                        .orElseThrow(ChildcareLikeHistoryNotFoundException::new);
        childcareLikeRepository.delete(childcareLike);
        log.info("게시글 좋아요 취소 완료");

        return SUCCESS_UNLIKE_BOARD;
    }
}
