package com.sidenow.domain.boardType.childCare.board.service;

import com.sidenow.domain.boardType.childCare.board.dto.req.ChildCareBoardRequest.ChildCareBoardRegisterPostRequest;
import com.sidenow.domain.boardType.childCare.board.dto.req.ChildCareBoardRequest.ChildCareBoardUpdatePostRequest;
import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse.AllChildCareBoards;
import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse.ChildCareBoardCheck;
import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse.ChildCareBoardGetPostListResponse;
import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse.ChildCareBoardGetPostResponse;
import com.sidenow.domain.boardType.childCare.board.entity.ChildCareBoard;
import com.sidenow.domain.boardType.childCare.board.exception.NotFoundChildCareBoardPostIdException;
import com.sidenow.domain.boardType.childCare.board.repository.ChildCareBoardFileRepository;
import com.sidenow.domain.boardType.childCare.board.repository.ChildCareBoardRepository;
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

import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class ChildCareBoardServiceImpl implements ChildCareBoardService{

    private final MemberRepository memberRepository;
    private final ChildCareBoardRepository childCareBoardRepository;
    private final ChildCareBoardFileRepository childCareBoardFileRepository;
    private final AwsS3Service awsS3Service;
    private final SecurityUtils securityUtils;
    private final int CHILDCARE_BOARD_PAGE_SIZE = 10;

    // 육아게시판 게시글 등록
    @Override
    public ChildCareBoardCheck registerChildCareBoardPost(List<MultipartFile> multipartFile, ChildCareBoardRegisterPostRequest createChildCareBoardPostRequest) {
        log.info("Register ChildCareBoard Post Service Start");
        ChildCareBoardCheck childCareBoardCheck = new ChildCareBoardCheck();
        Member findMember = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        ChildCareBoard childCareBoard = ChildCareBoardRegisterPostRequest.to(createChildCareBoardPostRequest, findMember);
        ChildCareBoardRepository.save(childCareBoard);
        if (multipartFile != null) {
            List<String> fileList = awsS3Service.uploadFile(findMember, childCareBoard, multipartFile);
            log.info("업로드 된 파일 리스트: "+fileList);
        }
        childCareBoardCheck.setSaved(true);
        log.info("Register ChildCareBoard Post Service End");

        return childCareBoardCheck;
    }

    // 육아게시판 게시글 단건 조회
    @Override
    public ChildCareBoardGetPostResponse getChildCareBoardPost(Long childCareBoardPostId){
        log.info("Get ChildCareBoard Post Service Start");
        ChildCareBoard findChildCareBoard = childCareBoardRepository.findByChildCareBoardPostId(childCareBoardPostId).orElseThrow(NotFoundChildCareBoardPostIdException::new);
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId());
        Map<String, String> files = new HashMap<>();
        childCareBoardFileRepository.findByChildCareBoard(findChildCareBoard).forEach(childCareBoardFile -> {
            String fileUrl = awsS3Service.getFilePath(childCareBoardFile.getNewFileName());
            files.put(childCareBoardFile.getOriginFileName(), fileUrl);
        });

        findChildCareBoard.increaseHits();

        ChildCareBoardGetPostResponse childCareBoardGetPostResponse = ChildCareBoardGetPostResponse.from(findChildCareBoard, files);
        log.info("Get ChildCareBoard Post Service End");
        return childCareBoardGetPostResponse;
    }

    // 육아게시판 게시글 전체 조회
    @Override
    public AllChildCareBoards getChildCareBoardPostList(Integer page){

        log.info("Get ChildCareBoard Post List Service 진입");
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId());
        if (page == null) {
            page = 1;
        }

        Pageable pageable = PageRequest.of(page-1, CHILDCARE_BOARD_PAGE_SIZE);

        Page<ChildCareBoard> childCareBoardPage = childCareBoardRepository.findByOrderByRegDateDesc(pageable);
        List<ChildCareBoardGetPostListResponse> childCareBoardGetPostList = new ArrayList<>();
        for (int i = 0; i < childCareBoardPage.getContent().size(); i++) {
            childCareBoardGetPostList.add(ChildCareBoardGetPostListResponse.from(childCareBoardPage.getContent().get(i)));
        }
        log.info("Get ChildCareBoard Post List Service 종료");

        return AllChildCareBoards.builder()
                        .childCareBoards(childCareBoardGetPostList)
                        .build();
    }

    // 육아게시판 게시글 수정
    @Override
    public ChildCareBoardCheck updateChildCareBoardPost(List<MultipartFile> multipartFile, Long childCareBoardPostId, ChildCareBoardUpdatePostRequest childCareBoardUpdatePostRequest){
        log.info("Update ChildCareBoard Post Service 진입");
        ChildCareBoardCheck childCareBoardCheck = new ChildCareBoardCheck();

        // 게시글 존재여부 확인
        ChildCareBoard findChildCareBoard = childCareBoardRepository.findByChildCareBoardPostId(childCareBoardPostId)
                .orElseThrow(NotFoundChildCareBoardPostIdException::new);

        // 게시글 작성자가 맞는지 확인
        Member findMember = memberRepository.findById(findChildCareBoard.getMember().getMemberId())
                .orElseThrow(MemberNotExistException::new);

        // S3 저장소 기존 첨부파일 삭제
        awsS3Service.deleteChildCareBoardFile(findChildCareBoard);

        if (multipartFile != null) {
            List<String> fileList = awsS3Service.uploadFile(findMember, findChildCareBoard, multipartFile);
            log.info("업로드된 파일리스트: "+fileList);
        }

        ChildCareBoard updateChildCareBoard = ChildCareBoardUpdatePostRequest.to(childCareBoardUpdatePostRequest, findMember);

        childCareBoardRepository.save(updateChildCareBoard);

        childCareBoardCheck.setUpdated(true);
        log.info("Update ChildCareBoard Post Service 종료");

        return childCareBoardCheck;
    }

    // 육아게시판 게시글 삭제
    @Override
    public ChildCareBoardCheck deleteChildCareBoardPost(Long childCareBoardPostId){
        log.info("Delete ChildCareBoard Post Service 진입");
        ChildCareBoardCheck childCareBoardCheck = new ChildCareBoardCheck();

        // 게시글 존재여부 확인
        ChildCareBoard findChildCareBoard = childCareBoardRepository.findByChildCareBoardPostId(childCareBoardPostId).orElseThrow(NotFoundChildCareBoardPostIdException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(findChildCareBoard.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        // S3 저장소 기존 첨부파일 삭제
        awsS3Service.deleteChildCareBoardFile(findChildCareBoard);

        childCareBoardRepository.delete(findChildCareBoard);

        childCareBoardCheck.setDeleted(true);
        log.info("Update ChildCareBoard Post Service 종료");

        return childCareBoardCheck;
    }
}
