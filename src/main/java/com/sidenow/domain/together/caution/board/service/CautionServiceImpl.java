package com.sidenow.domain.together.caution.board.service;

import com.sidenow.domain.together.caution.board.entity.Caution;
import com.sidenow.domain.together.caution.board.entity.CautionLike;
import com.sidenow.domain.together.caution.board.exception.CautionIdNotFoundException;
import com.sidenow.domain.together.caution.board.exception.CautionLikeHistoryNotFoundException;
import com.sidenow.domain.together.caution.board.repository.CautionLikeRepository;
import com.sidenow.domain.together.caution.board.repository.CautionRepository;
import com.sidenow.domain.together.caution.board.dto.req.CautionRequest.CautionCreateRequest;
import com.sidenow.domain.together.caution.board.dto.req.CautionRequest.CautionUpdateRequest;
import com.sidenow.domain.together.caution.board.dto.res.CautionResponse.*;
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
public class CautionServiceImpl implements CautionService {

    private static final String SUCCESS_LIKE_BOARD = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_BOARD = "좋아요 취소 완료";
    private static final int CAUTION_PAGE_SIZE = 10;

    private final MemberRepository memberRepository;
    private final CautionRepository cautionRepository;
    private final CautionLikeRepository cautionLikeRepository;
    private final AwsS3Service awsS3Service;
    private final SecurityUtils securityUtils;

    // 조심해요 게시글 등록
    @Override
    @Transactional
    public CautionCreateResponse createCaution(CautionCreateRequest req, MultipartFile image) {
        log.info("Create Caution Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getId()).get();

        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        String imgUrl = "";
        if (image != null){
            imgUrl = awsS3Service.uploadFile(image);
            log.info("업로드 된 파일: "+imgUrl);
        }

        Caution caution = CautionCreateRequest.to(req, member, imgUrl);
        cautionRepository.save(caution);

        log.info("Create Caution Service 종료");

        return CautionCreateResponse.from(caution);
    }

    // 조심해요 게시글 단건 조회
    @Override
    @Transactional
    public CautionGetResponse getCaution(Long id){
        log.info("Get Caution Service 진입");
        Caution caution = cautionRepository.findById(id).orElseThrow(CautionIdNotFoundException::new);
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getId());

        caution.increaseHits();

        CautionGetResponse cautionGetResponse = CautionGetResponse.from(caution);
        log.info("Get Caution Post Service End");
        return cautionGetResponse;
    }

    // 조심해요 게시글 전체 조회
    @Override
    @Transactional
    public AllCautions getCautionList(Integer page){

        log.info("Get Caution Post List Service 진입");
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getId());
        if (page == null) {
            page = 1;
        }

        Pageable pageable = PageRequest.of(page-1, CAUTION_PAGE_SIZE);

        Page<Caution> cautionPage = cautionRepository.findByOrderByCreatedAtDesc(pageable);
        List<CautionGetListResponse> cautionGetPostList = new ArrayList<>();
        for (int i = 0; i < cautionPage.getContent().size(); i++) {
            cautionGetPostList.add(CautionGetListResponse.from(cautionPage.getContent().get(i)));
        }
        log.info("Get Caution Post List Service 종료");

        return AllCautions.builder()
                        .cautions(cautionGetPostList)
                        .build();
    }

    // 조심해요 게시글 수정
    @Override
    @Transactional
    public CautionUpdateResponse updateCaution(Long id, CautionUpdateRequest req, MultipartFile image){
        log.info("Update Caution Service 진입");

        // 게시글 존재여부 확인
        Caution caution = cautionRepository.findById(id)
                .orElseThrow(CautionIdNotFoundException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(caution.getMember().getId()).orElseThrow(MemberNotExistException::new);

        if (image != null){
            // 기존 이미지 파일 삭제
            awsS3Service.deleteFile(image.getOriginalFilename());
            String imgUrl = awsS3Service.uploadFile(image);
            log.info("업로드된 파일: "+imgUrl);
            caution.updateImage(imgUrl);
        }

        if (req.getTitle() != null){
            caution.updateTitle(req.getTitle());
        }

        if (req.getContent() != null) {
            caution.updateContent(req.getContent());
        }

        cautionRepository.save(caution);

        log.info("Update Caution Service 종료");

        return CautionUpdateResponse.from(caution);
    }

    // 조심해요 게시글 삭제
    @Override
    @Transactional
    public void deleteCaution(Long id){
        log.info("Delete Caution Post Service 진입");

        // 게시글 존재여부 확인
        Caution caution = cautionRepository.findById(id).orElseThrow(CautionIdNotFoundException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(caution.getMember().getId()).orElseThrow(MemberNotExistException::new);

        cautionRepository.delete(caution);

        log.info("Delete Caution Post Service 종료");
    }

    // 조심해요 게시글 좋아요
    @Override
    @Transactional
    public String updateLikeOfCaution(Long id) {
        log.info("Update Like Of Caution Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getId()).get();
        Caution caution = cautionRepository.findById(id).orElseThrow(CautionIdNotFoundException::new);
        
        if (!hasLikeCaution(caution, member)){
            caution.increaseLikes();
            return createLikeCaution(caution, member);
        }

        caution.decreaseLikes();
        return removeLikeCaution(caution, member);
    }
    
    private boolean hasLikeCaution(Caution caution, Member member){
        return cautionLikeRepository.findByCautionAndMember(caution, member).isPresent();
    }

    private String createLikeCaution(Caution caution, Member member) {
        CautionLike cautionLike = new CautionLike(caution, member);
        cautionLikeRepository.save(cautionLike);
        log.info("게시글 좋아요 완료");
        return SUCCESS_LIKE_BOARD;
    }

    private String removeLikeCaution(Caution caution, Member member) {
        CautionLike cautionLike = cautionLikeRepository.findByCautionAndMember(caution, member)
                        .orElseThrow(CautionLikeHistoryNotFoundException::new);
        cautionLikeRepository.delete(cautionLike);
        log.info("게시글 좋아요 취소 완료");

        return SUCCESS_UNLIKE_BOARD;
    }
}
