package com.sidenow.domain.together.vote.board.service;

import com.sidenow.domain.together.vote.board.dto.req.VoteRequest.VoteCreateRequest;
import com.sidenow.domain.together.vote.board.dto.req.VoteRequest.VoteUpdateRequest;
import com.sidenow.domain.together.vote.board.dto.res.VoteResponse.*;
import com.sidenow.domain.together.vote.board.entity.Vote;
import com.sidenow.domain.together.vote.board.entity.VoteLike;
import com.sidenow.domain.together.vote.board.exception.VoteIdNotFoundException;
import com.sidenow.domain.together.vote.board.exception.VoteLikeHistoryNotFoundException;
import com.sidenow.domain.together.vote.board.repository.VoteLikeRepository;
import com.sidenow.domain.together.vote.board.repository.VoteRepository;
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
public class VoteServiceImpl implements VoteService {

    private static final String SUCCESS_LIKE_BOARD = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_BOARD = "좋아요 취소 완료";
    private static final int VOTE_PAGE_SIZE = 10;

    private final MemberRepository memberRepository;
    private final VoteRepository voteRepository;
    private final VoteLikeRepository voteLikeRepository;
    private final AwsS3Service awsS3Service;
    private final SecurityUtils securityUtils;

    // 투표해요 게시글 등록
    @Override
    @Transactional
    public VoteCreateResponse createVote(VoteCreateRequest req, MultipartFile image) {
        log.info("Create Vote Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();

        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        String imgUrl = "";
        if (image != null){
            imgUrl = awsS3Service.uploadFile(image);
            log.info("업로드 된 파일: "+imgUrl);
        }

        Vote vote = VoteCreateRequest.to(req, member, imgUrl);
        voteRepository.save(vote);

        log.info("Create Vote Service 종료");

        return VoteCreateResponse.from(vote);
    }

    // 투표해요 게시글 단건 조회
    @Override
    @Transactional
    public VoteGetResponse getVote(Long id){
        log.info("Get Vote Service 진입");
        Vote vote = voteRepository.findById(id).orElseThrow(VoteIdNotFoundException::new);
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId());

        vote.increaseHits();

        VoteGetResponse voteGetResponse = VoteGetResponse.from(vote);
        log.info("Get Vote Post Service End");
        return voteGetResponse;
    }

    // 투표해요 게시글 전체 조회
    @Override
    @Transactional
    public AllVotes getVoteList(Integer page){

        log.info("Get Vote Post List Service 진입");
        memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new).getMemberId());
        if (page == null) {
            page = 1;
        }

        Pageable pageable = PageRequest.of(page-1, VOTE_PAGE_SIZE);

        Page<Vote> votePage = voteRepository.findByOrderByCreatedAtDesc(pageable);
        List<VoteGetListResponse> voteGetPostList = new ArrayList<>();
        for (int i = 0; i < votePage.getContent().size(); i++) {
            voteGetPostList.add(VoteGetListResponse.from(votePage.getContent().get(i)));
        }
        log.info("Get Vote Post List Service 종료");

        return AllVotes.builder()
                        .votes(voteGetPostList)
                        .build();
    }

    // 투표해요 게시글 수정
    @Override
    @Transactional
    public VoteUpdateResponse updateVote(Long id, VoteUpdateRequest req, MultipartFile image){
        log.info("Update Vote Service 진입");

        // 게시글 존재여부 확인
        Vote vote = voteRepository.findById(id)
                .orElseThrow(VoteIdNotFoundException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(vote.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        if (image != null){
            // 기존 이미지 파일 삭제
            awsS3Service.deleteFile(image.getOriginalFilename());
            String imgUrl = awsS3Service.uploadFile(image);
            log.info("업로드된 파일: "+imgUrl);
            vote.updateImage(imgUrl);
        }

        if (req.getTitle() != null){
            vote.updateTitle(req.getTitle());
        }

        if (req.getContent() != null) {
            vote.updateContent(req.getContent());
        }

        voteRepository.save(vote);

        log.info("Update Vote Service 종료");

        return VoteUpdateResponse.from(vote);
    }

    // 투표해요 게시글 삭제
    @Override
    @Transactional
    public void deleteVote(Long id){
        log.info("Delete Vote Post Service 진입");

        // 게시글 존재여부 확인
        Vote vote = voteRepository.findById(id).orElseThrow(VoteIdNotFoundException::new);

        // 게시글 작성자가 맞는지 확인
        memberRepository.findById(vote.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        voteRepository.delete(vote);

        log.info("Delete Vote Post Service 종료");
    }

    // 투표해요 게시글 좋아요
    @Override
    @Transactional
    public String updateLikeOfVote(Long id) {
        log.info("Update Like Of Vote Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        Vote vote = voteRepository.findById(id).orElseThrow(VoteIdNotFoundException::new);
        
        if (!hasLikeVote(vote, member)){
            vote.increaseLikes();
            return createLikeVote(vote, member);
        }

        vote.decreaseLikes();
        return removeLikeVote(vote, member);
    }
    
    private boolean hasLikeVote(Vote vote, Member member){
        return voteLikeRepository.findByVoteAndMember(vote, member).isPresent();
    }

    private String createLikeVote(Vote vote, Member member) {
        VoteLike voteLike = new VoteLike(vote, member);
        voteLikeRepository.save(voteLike);
        log.info("게시글 좋아요 완료");
        return SUCCESS_LIKE_BOARD;
    }

    private String removeLikeVote(Vote vote, Member member) {
        VoteLike voteLike = voteLikeRepository.findByVoteAndMember(vote, member)
                        .orElseThrow(VoteLikeHistoryNotFoundException::new);
        voteLikeRepository.delete(voteLike);
        log.info("게시글 좋아요 취소 완료");

        return SUCCESS_UNLIKE_BOARD;
    }
}
