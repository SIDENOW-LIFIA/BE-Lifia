package com.sidenow.domain.boardType.free.board.service;

import com.sidenow.domain.boardType.free.board.dto.req.FreeBoardRequest.FreeBoardRegisterPostRequest;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardCheck;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardGetPostListResponse;
import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse.FreeBoardGetPostResponse;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.exception.NotFoundFreeBoardPostIdException;
import com.sidenow.domain.boardType.free.board.repository.FreeBoardFileRepository;
import com.sidenow.domain.boardType.free.board.repository.FreeBoardRepository;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberEmailNotFoundException;
import com.sidenow.domain.member.exception.NotFoundMemberException;
import com.sidenow.domain.member.repository.MemberRepository;
import com.sidenow.global.config.aws.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class FreeBoardServiceImpl implements FreeBoardService{

    private final MemberRepository memberRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardFileRepository freeBoardFileRepository;
    private final AwsS3Service awsS3Service;

    // 자유게시판 게시글 등록
    @Override
    public FreeBoardCheck registerFreeBoardPost(List<MultipartFile> multipartFile, FreeBoardRegisterPostRequest createFreeBoardPostRequest) {
        log.info("Register FreeBoard Post Service Start");
        FreeBoardCheck freeBoardCheck = new FreeBoardCheck();
        Member findMember = memberRepository.findById(createFreeBoardPostRequest.getMemberId()).orElseThrow(MemberEmailNotFoundException::new);
        FreeBoard freeBoard = FreeBoardRegisterPostRequest.to(createFreeBoardPostRequest, findMember);
        freeBoardRepository.save(freeBoard);
        if (multipartFile != null) {
            awsS3Service.uploadFile(findMember, freeBoard, multipartFile);
        }
        freeBoardCheck.setSaved(true);
        log.info("Register FreeBoard Post Service End");
        return freeBoardCheck;
    }

    // 자유게시판 게시글 단건 조회
    @Override
    public FreeBoardGetPostResponse getFreeBoardPost(Long freeBoardPostId){
        log.info("Get FreeBoard Post Service Start");
        FreeBoard findFreeBoard = freeBoardRepository.findByFreeBoardPostId(freeBoardPostId).orElseThrow(NotFoundFreeBoardPostIdException::new);
        memberRepository.findById(findFreeBoard.getMember().getMemberId()).orElseThrow(MemberEmailNotFoundException::new);
        Map<String, String> files = new HashMap<>();

        freeBoardFileRepository.findByFreeBoard(findFreeBoard).forEach(freeBoardFile -> {
            String fileUrl = awsS3Service.getFilePath(freeBoardFile.getNewFileName());
            files.put(freeBoardFile.getOriginFileName(), fileUrl);
        });

        findFreeBoard.increaseHits();

        FreeBoardGetPostResponse freeBoardGetPostResponse = FreeBoardGetPostResponse.from(findFreeBoard, files);
        log.info("Get FreeBoard Post Service End");
        return freeBoardGetPostResponse;
    }

    // 자유게시판 게시글 전체 조회
    @Override
    public List<FreeBoardGetPostListResponse> getFreeBoardPostList(String accessToken, int pageNumber){

    }

    // 자유게시판 게시글 수정
    @Override
    public FreeBoardCheck updateFreeBoardPost(String accessToken, List<MultipartFile> multipartFile, FreeBoardRegisterPostRequest freeBoardCreatePostRequest){

    }

    // 자유게시판 게시글 삭제
    @Override
    public FreeBoardCheck deleteFreeBoardPost(Long freeBoardPostId){

    }
}
