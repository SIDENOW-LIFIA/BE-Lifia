package com.sidenow.domain.boardType.vote.board.service;

import com.sidenow.domain.boardType.vote.board.dto.req.VoteBoardRequest.VoteBoardRegisterPostRequest;
import com.sidenow.domain.boardType.vote.board.dto.req.VoteBoardRequest.VoteBoardUpdatePostRequest;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse.AllVoteBoards;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse.VoteBoardCheck;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse.VoteBoardGetPostListResponse;
import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse.VoteBoardGetPostResponse;
import com.sidenow.domain.boardType.vote.board.entity.VoteBoard;
import com.sidenow.domain.boardType.vote.board.exception.NotFoundVoteBoardPostIdException;
import com.sidenow.domain.boardType.vote.board.repository.VoteBoardFileRepository;
import com.sidenow.domain.boardType.vote.board.repository.VoteBoardRepository;
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
public class VoteBoardServiceImpl implements VoteBoardService{

    private final MemberRepository memberRepository;
    private final VoteBoardRepository voteBoardRepository;
    private final VoteBoardFileRepository voteBoardFileRepository;
    private final AwsS3Service awsS3Service;
    private final SecurityUtils securityUtils;
    private final int VOTE_BOARD_PAGE_SIZE = 10;

    // 투표게시판 게시글 등록
    @Override
    public VoteBoardCheck registerVoteBoardPost(List<MultipartFile> multipartFile, VoteBoardRegisterPostRequest createVoteBoardPostRequest) {
        log.info("Register VoteBoard Post Service Start");
        VoteBoardCheck voteBoardCheck = new VoteBoardCheck();
        Member findMember = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        VoteBoard voteBoard = VoteBoardRegisterPostRequest.to(createVoteBoardPostRequest, findMember);
        voteBoardRepository.save(voteBoard);
        if (multipartFile != null) {
            List<String> fileList = awsS3Service.uploadFile(findMember, voteBoard, multipartFile);
            log.info("업로드 된 파일 리스트: "+fileList);
        }
        voteBoardCheck.setSaved(true);
        log.info("Register VoteBoard Post Service End");

        return voteBoardCheck;
    }

    // 투표게시판 게시글 단건 조회
    @Override
    public VoteBoardGetPostResponse getVoteBoardPost(Long voteBoardPostId){
        log.info("Get VoteBoard Post Service Start");
        VoteBoard findVoteBoard = voteBoardRepository.findByVoteBoardPostId(voteBoardPostId).orElseThrow(NotFoundVoteBoardPostIdException::new);
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId());
        Map<String, String> files = new HashMap<>();
        voteBoardFileRepository.findByVoteBoard(findVoteBoard).forEach(voteBoardFile -> {
            String fileUrl = awsS3Service.getFilePath(voteBoardFile.getNewFileName());
            files.put(voteBoardFile.getOriginFileName(), fileUrl);
        });

        findVoteBoard.increaseHits();

        VoteBoardGetPostResponse voteBoardGetPostResponse = VoteBoardGetPostResponse.from(findVoteBoard, files);
        log.info("Get VoteBoard Post Service End");
        return voteBoardGetPostResponse;
    }

    // 투표게시판 게시글 전체 조회
    @Override
    public AllVoteBoards getVoteBoardPostList(Integer page){

        log.info("Get VoteBoard Post List Service 진입");
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId());
        if (page == null) {
            page = 1;
        }

        Pageable pageable = PageRequest.of(page-1, VOTE_BOARD_PAGE_SIZE);

        Page<VoteBoard> voteBoardPage = voteBoardRepository.findByOrderByRegDateDesc(pageable);
        List<VoteBoardGetPostListResponse> voteBoardGetPostList = new ArrayList<>();
        for (int i = 0; i < voteBoardPage.getContent().size(); i++) {
            voteBoardGetPostList.add(VoteBoardGetPostListResponse.from(voteBoardPage.getContent().get(i)));
        }
        log.info("Get VoteBoard Post List Service 종료");

        return AllVoteBoards.builder()
                        .voteBoards(voteBoardGetPostList)
                        .build();
    }

    // 투표게시판 게시글 수정
    @Override
    public VoteBoardCheck updateVoteBoardPost(List<MultipartFile> multipartFile, Long voteBoardPostId, VoteBoardUpdatePostRequest voteBoardUpdatePostRequest){
        log.info("Update VoteBoard Post Service 진입");
        VoteBoardCheck voteBoardCheck = new VoteBoardCheck();

        // 게시글 존재여부 확인
        VoteBoard findVoteBoard = voteBoardRepository.findByVoteBoardPostId(voteBoardPostId)
                .orElseThrow(NotFoundVoteBoardPostIdException::new);

        // 게시글 작성자가 맞는지 확인
        Member findMember = memberRepository.findById(findVoteBoard.getMember().getMemberId())
                .orElseThrow(MemberNotExistException::new);

        // S3 저장소 기존 첨부파일 삭제
        awsS3Service.deleteVoteBoardFile(findVoteBoard);

        if (multipartFile != null) {
            List<String> fileList = awsS3Service.uploadFile(findMember, findVoteBoard, multipartFile);
            log.info("업로드된 파일리스트: "+fileList);
        }

        VoteBoard updateVoteBoard = VoteBoardUpdatePostRequest.to(voteBoardUpdatePostRequest, findMember);

        voteBoardRepository.save(updateVoteBoard);

        voteBoardCheck.setUpdated(true);
        log.info("Update VoteBoard Post Service 종료");

        return voteBoardCheck;
    }

    // 투표게시판 게시글 삭제
    @Override
    public VoteBoardCheck deleteVoteBoardPost(Long voteBoardPostId){
        log.info("Delete VoteBoard Post Service 진입");
        VoteBoardCheck voteBoardCheck = new VoteBoardCheck();

        // 게시글 존재여부 확인
        VoteBoard findVoteBoard = voteBoardRepository.findByVoteBoardPostId(voteBoardPostId).orElseThrow(NotFoundVoteBoardPostIdException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(findVoteBoard.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        // S3 저장소 기존 첨부파일 삭제
        awsS3Service.deleteVoteBoardFile(findVoteBoard);

        voteBoardRepository.delete(findVoteBoard);

        voteBoardCheck.setDeleted(true);
        log.info("Update VoteBoard Post Service 종료");

        return voteBoardCheck;
    }
}
