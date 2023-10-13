package com.sidenow.domain.boardType.free.board.service;

import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardCreateRequest;
import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardUpdateRequest;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.*;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.entity.FreeBoardLike;
import com.sidenow.domain.boardType.free.board.exception.FreeBoardIdNotFoundException;
import com.sidenow.domain.boardType.free.board.exception.FreeBoardLikeHistoryNotFoundException;
import com.sidenow.domain.boardType.free.board.repository.FreeBoardLikeRepository;
import com.sidenow.domain.boardType.free.board.repository.FreeBoardRepository;
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
public class FreeBoardServiceImpl implements FreeBoardService{

    private static final String SUCCESS_LIKE_BOARD = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_BOARD = "좋아요 취소 완료";
    private static final int FREE_BOARD_PAGE_SIZE = 10;

    private final MemberRepository memberRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardLikeRepository freeBoardLikeRepository;
    private final AwsS3Service awsS3Service;
    private final SecurityUtils securityUtils;

    // 자유게시판 게시글 등록
    @Override
    @Transactional
    public FreeBoardCreateResponse createFreeBoard(FreeBoardCreateRequest req, MultipartFile image) {
        log.info("Create FreeBoard Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();

        String imgUrl = awsS3Service.uploadFile(image);
        log.info("업로드 된 파일: "+imgUrl);

        FreeBoard freeBoard = FreeBoardCreateRequest.to(req, imgUrl, member);
        freeBoardRepository.save(freeBoard);

        log.info("Create FreeBoard Service 종료");

        return FreeBoardCreateResponse.from(freeBoard);
    }

    // 자유게시판 게시글 단건 조회
    @Override
    @Transactional
    public FreeBoardGetResponse getFreeBoard(Long id){
        log.info("Get FreeBoard Service 진입");
        FreeBoard freeBoard = freeBoardRepository.findById(id).orElseThrow(FreeBoardIdNotFoundException::new);
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId());

        freeBoard.increaseHits();

        FreeBoardGetResponse freeBoardGetResponse = FreeBoardGetResponse.from(freeBoard);
        log.info("Get FreeBoard Post Service End");
        return freeBoardGetResponse;
    }

    // 자유게시판 게시글 전체 조회
    @Override
    @Transactional
    public AllFreeBoards getFreeBoardList(Integer page){

        log.info("Get FreeBoard Post List Service 진입");
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId());
        if (page == null) {
            page = 1;
        }

        Pageable pageable = PageRequest.of(page-1, FREE_BOARD_PAGE_SIZE);

        Page<FreeBoard> freeBoardPage = freeBoardRepository.findByOrderByRegDateDesc(pageable);
        List<FreeBoardGetListResponse> freeBoardGetPostList = new ArrayList<>();
        for (int i = 0; i < freeBoardPage.getContent().size(); i++) {
            freeBoardGetPostList.add(FreeBoardGetListResponse.from(freeBoardPage.getContent().get(i)));
        }
        log.info("Get FreeBoard Post List Service 종료");

        return AllFreeBoards.builder()
                        .freeBoards(freeBoardGetPostList)
                        .build();
    }

    // 자유게시판 게시글 수정
    @Override
    @Transactional
    public FreeBoardUpdateResponse updateFreeBoard(Long id, FreeBoardUpdateRequest req, MultipartFile image){
        log.info("Update FreeBoard Service 진입");

        // 게시글 존재여부 확인
        FreeBoard freeBoard = freeBoardRepository.findById(id)
                .orElseThrow(FreeBoardIdNotFoundException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(freeBoard.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        if (image != null){
            // 기존 이미지 파일 삭제
            awsS3Service.deleteFile(image.getOriginalFilename());
            String imgUrl = awsS3Service.uploadFile(image);
            log.info("업로드된 파일: "+imgUrl);
            freeBoard.updateImage(imgUrl);
        }

        if (req.getTitle() != null){
            freeBoard.updateTitle(req.getTitle());
        }

        if (req.getContent() != null) {
            freeBoard.updateContent(req.getContent());
        }

        if (req.getRegDate() != null) {
            freeBoard.updateRegDate(req.getRegDate());
        }

        log.info("Update FreeBoard Service 종료");

        return FreeBoardUpdateResponse.from(freeBoard);
    }

    // 자유게시판 게시글 삭제
    @Override
    @Transactional
    public void deleteFreeBoard(Long id){
        log.info("Delete FreeBoard Post Service 진입");

        // 게시글 존재여부 확인
        FreeBoard freeBoard = freeBoardRepository.findById(id).orElseThrow(FreeBoardIdNotFoundException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(freeBoard.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        freeBoardRepository.delete(freeBoard);

        log.info("Delete FreeBoard Post Service 종료");
    }

    // 자유게시판 게시글 좋아요
    @Override
    @Transactional
    public String updateLikeOfFreeBoard(Long id) {
        log.info("Update Like Of FreeBoard Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        FreeBoard freeBoard = freeBoardRepository.findById(id).orElseThrow(FreeBoardIdNotFoundException::new);
        
        if (!hasLikeFreeBoard(freeBoard, member)){
            freeBoard.increaseLikes();
            return createLikeFreeBoard(freeBoard, member);
        }

        freeBoard.decreaseLikes();
        return removeLikeFreeBoard(freeBoard, member);
    }
    
    public boolean hasLikeFreeBoard(FreeBoard freeBoard, Member member){
        return freeBoardLikeRepository.findByFreeBoardAndMember(freeBoard, member).isPresent();
    }

    public String createLikeFreeBoard(FreeBoard freeBoard, Member member) {
        FreeBoardLike freeBoardLike = new FreeBoardLike(freeBoard, member);
        freeBoardLikeRepository.save(freeBoardLike);
        log.info("좋아요 완료");
        return SUCCESS_LIKE_BOARD;
    }

    public String removeLikeFreeBoard(FreeBoard freeBoard, Member member) {
        FreeBoardLike freeBoardLike = freeBoardLikeRepository.findByFreeBoardAndMember(freeBoard, member)
                        .orElseThrow(FreeBoardLikeHistoryNotFoundException::new);
        freeBoardLikeRepository.delete(freeBoardLike);
        log.info("좋아요 취소 완료");

        return SUCCESS_UNLIKE_BOARD;
    }
}
