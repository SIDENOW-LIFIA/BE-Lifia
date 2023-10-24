package com.sidenow.domain.boardType.coBuying.board.service;

import com.sidenow.domain.boardType.coBuying.board.dto.req.CoBuyingRequest.CoBuyingCreateRequest;
import com.sidenow.domain.boardType.coBuying.board.dto.req.CoBuyingRequest.CoBuyingUpdateRequest;
import com.sidenow.domain.boardType.coBuying.board.dto.res.CoBuyingResponse.*;
import com.sidenow.domain.boardType.coBuying.board.entity.CoBuying;
import com.sidenow.domain.boardType.coBuying.board.entity.CoBuyingLike;
import com.sidenow.domain.boardType.coBuying.board.exception.CoBuyingIdNotFoundException;
import com.sidenow.domain.boardType.coBuying.board.exception.CoBuyingLikeHistoryNotFoundException;
import com.sidenow.domain.boardType.coBuying.board.repository.CoBuyingLikeRepository;
import com.sidenow.domain.boardType.coBuying.board.repository.CoBuyingRepository;
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
public class CoBuyingServiceImpl implements CoBuyingService {

    private static final String SUCCESS_LIKE_BOARD = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_BOARD = "좋아요 취소 완료";
    private static final int CO_BUYING_PAGE_SIZE = 10;

    private final MemberRepository memberRepository;
    private final CoBuyingRepository coBuyingRepository;
    private final CoBuyingLikeRepository coBuyingLikeRepository;
    private final AwsS3Service awsS3Service;
    private final SecurityUtils securityUtils;

    // 공구해요 게시글 등록
    @Override
    @Transactional
    public CoBuyingCreateResponse createCoBuying(CoBuyingCreateRequest req, MultipartFile image) {
        log.info("Create CoBuying Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();

        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        String imgUrl = "";
        if (image != null){
            imgUrl = awsS3Service.uploadFile(image);
            log.info("업로드 된 파일: "+imgUrl);
        }

        CoBuying coBuying = CoBuyingCreateRequest.to(req, member, imgUrl);
        coBuyingRepository.save(coBuying);

        log.info("Create CoBuying Service 종료");

        return CoBuyingCreateResponse.from(coBuying);
    }

    // 공구해요 게시글 단건 조회
    @Override
    @Transactional
    public CoBuyingGetResponse getCoBuying(Long id){
        log.info("Get CoBuying Service 진입");
        CoBuying coBuying = coBuyingRepository.findById(id).orElseThrow(CoBuyingIdNotFoundException::new);
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId());

        coBuying.increaseHits();

        CoBuyingGetResponse coBuyingGetResponse = CoBuyingGetResponse.from(coBuying);
        log.info("Get CoBuying Post Service End");
        return coBuyingGetResponse;
    }

    // 공구해요 게시글 전체 조회
    @Override
    @Transactional
    public AllCoBuyings getCoBuyingList(Integer page){

        log.info("Get CoBuying Post List Service 진입");
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId());
        if (page == null) {
            page = 1;
        }

        Pageable pageable = PageRequest.of(page-1, CO_BUYING_PAGE_SIZE);

        Page<CoBuying> coBuyingPage = coBuyingRepository.findByOrderByCreatedAtDesc(pageable);
        List<CoBuyingGetListResponse> coBuyingGetPostList = new ArrayList<>();
        for (int i = 0; i < coBuyingPage.getContent().size(); i++) {
            coBuyingGetPostList.add(CoBuyingGetListResponse.from(coBuyingPage.getContent().get(i)));
        }
        log.info("Get CoBuying Post List Service 종료");

        return AllCoBuyings.builder()
                        .coBuyings(coBuyingGetPostList)
                        .build();
    }

    // 공구해요 게시글 수정
    @Override
    @Transactional
    public CoBuyingUpdateResponse updateCoBuying(Long id, CoBuyingUpdateRequest req, MultipartFile image){
        log.info("Update CoBuying Service 진입");

        // 게시글 존재여부 확인
        CoBuying coBuying = coBuyingRepository.findById(id)
                .orElseThrow(CoBuyingIdNotFoundException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(coBuying.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        if (image != null){
            // 기존 이미지 파일 삭제
            awsS3Service.deleteFile(image.getOriginalFilename());
            String imgUrl = awsS3Service.uploadFile(image);
            log.info("업로드된 파일: "+imgUrl);
            coBuying.updateImage(imgUrl);
        }

        if (req.getTitle() != null){
            coBuying.updateTitle(req.getTitle());
        }

        if (req.getContent() != null) {
            coBuying.updateContent(req.getContent());
        }

        coBuyingRepository.save(coBuying);

        log.info("Update CoBuying Service 종료");

        return CoBuyingUpdateResponse.from(coBuying);
    }

    // 공구해요 게시글 삭제
    @Override
    @Transactional
    public void deleteCoBuying(Long id){
        log.info("Delete CoBuying Post Service 진입");

        // 게시글 존재여부 확인
        CoBuying coBuying = coBuyingRepository.findById(id).orElseThrow(CoBuyingIdNotFoundException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(coBuying.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        coBuyingRepository.delete(coBuying);

        log.info("Delete CoBuying Post Service 종료");
    }

    // 공구해요 게시글 좋아요
    @Override
    @Transactional
    public String updateLikeOfCoBuying(Long id) {
        log.info("Update Like Of CoBuying Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        CoBuying coBuying = coBuyingRepository.findById(id).orElseThrow(CoBuyingIdNotFoundException::new);
        
        if (!hasLikeCoBuying(coBuying, member)){
            coBuying.increaseLikes();
            return createLikeCoBuying(coBuying, member);
        }

        coBuying.decreaseLikes();
        return removeLikeCoBuying(coBuying, member);
    }
    
    private boolean hasLikeCoBuying(CoBuying coBuying, Member member){
        return coBuyingLikeRepository.findByCoBuyingAndMember(coBuying, member).isPresent();
    }

    private String createLikeCoBuying(CoBuying coBuying, Member member) {
        CoBuyingLike coBuyingLike = new CoBuyingLike(coBuying, member);
        coBuyingLikeRepository.save(coBuyingLike);
        log.info("게시글 좋아요 완료");
        return SUCCESS_LIKE_BOARD;
    }

    private String removeLikeCoBuying(CoBuying coBuying, Member member) {
        CoBuyingLike coBuyingLike = coBuyingLikeRepository.findByCoBuyingAndMember(coBuying, member)
                        .orElseThrow(CoBuyingLikeHistoryNotFoundException::new);
        coBuyingLikeRepository.delete(coBuyingLike);
        log.info("게시글 좋아요 취소 완료");

        return SUCCESS_UNLIKE_BOARD;
    }
}
