package com.sidenow.domain.boardType.vote.comment.service;

import com.sidenow.domain.boardType.vote.comment.dto.req.VoteCommentRequest.VoteCommentCreateRequest;
import com.sidenow.domain.boardType.vote.comment.dto.req.VoteCommentRequest.VoteCommentUpdateRequest;
import com.sidenow.domain.boardType.vote.comment.dto.res.VoteCommentResponse.VoteGetCommentListResponse;
import com.sidenow.domain.boardType.vote.board.entity.Vote;
import com.sidenow.domain.boardType.vote.board.exception.VoteIdNotFoundException;
import com.sidenow.domain.boardType.vote.board.repository.VoteRepository;
import com.sidenow.domain.boardType.vote.comment.entity.VoteComment;
import com.sidenow.domain.boardType.vote.comment.entity.VoteCommentLike;
import com.sidenow.domain.boardType.vote.comment.exception.VoteCommentIdNotFoundException;
import com.sidenow.domain.boardType.vote.comment.exception.VoteCommentLikeHistoryNotFoundException;
import com.sidenow.domain.boardType.vote.comment.repository.VoteCommentLikeRepository;
import com.sidenow.domain.boardType.vote.comment.repository.VoteCommentRepository;
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

import static com.sidenow.domain.boardType.vote.comment.dto.res.VoteCommentResponse.VoteCommentCreateResponse;
import static com.sidenow.domain.boardType.vote.comment.dto.res.VoteCommentResponse.VoteCommentUpdateResponse;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class VoteCommentServiceImpl implements VoteCommentService {

    private static final String SUCCESS_LIKE_COMMENT = "좋아요 처리 완료";
    private static final String SUCCESS_UNLIKE_COMMENT = "좋아요 취소 완료";
    private final MemberRepository memberRepository;
    private final VoteRepository voteRepository;
    private final VoteCommentRepository voteCommentRepository;
    private final VoteCommentLikeRepository voteCommentLikeRepository;
    private final SecurityUtils securityUtils;

    @Override
    // 투표해요 게시글 댓글 등록
    public VoteCommentCreateResponse createVoteComment(Long voteId, VoteCommentCreateRequest req) {
        log.info("Create Vote Comment Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        Vote vote = voteRepository.findByVoteId(voteId).orElseThrow(VoteIdNotFoundException::new);
        VoteComment voteComment;
        if (req.getParentId() == null) {
            voteComment = createVoteParentComments(req, member, vote);
        } else {
            voteComment = createVoteChildComments(req, member, vote);
        }
        voteCommentRepository.save(voteComment);
        log.info("Create Vote Comment Service 종료");
        return VoteCommentCreateResponse.from(voteComment);
    }

    @Override
    // 투표해요 게시글의 댓글 전체 조회
    public List<VoteGetCommentListResponse> getVoteCommentList(Long voteId) {
        log.info("Read Vote Comment Service Start");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());
        Vote vote = voteRepository.findByVoteId(voteId).orElseThrow(VoteIdNotFoundException::new);
        List<VoteComment> voteCommentsList = voteCommentRepository.findAllByVote_VoteIdOrderByCreatedAtAsc(vote.getVoteId());
        List<VoteGetCommentListResponse> readVoteCommentDto = new ArrayList<>();
        voteCommentsList.forEach(s -> readVoteCommentDto.add(VoteGetCommentListResponse.from(s))); // 댓글 전체 조회 핵심 코드 (람다식 forEach 사용)
        log.info("Read Vote Comment Service Start");
        return readVoteCommentDto;
    }

    @Override
    // 투표해요 게시글의 댓글 삭제
    public void deleteVoteComment(Long voteId, Long voteCommentId) {
        log.info("Delete Vote Comment Service 진입");

        // 게시글 존재여부 확인
        voteRepository.findByVoteId(voteId).orElseThrow(VoteIdNotFoundException::new);

        // 댓글 존재여부 확인
        VoteComment voteComment = voteCommentRepository.findById(voteCommentId).orElseThrow(VoteCommentIdNotFoundException::new);

        // 댓글 작성자가 맞는지 확인
        memberRepository.findById(voteComment.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        voteComment.changeIsDeleted(true);
        voteCommentRepository.deleteById(voteComment.getCommentId()); // 댓글 삭제
        log.info("Delete Vote Comment Service 종료");
    }

    @Override
    // 투표해요 댓글 수정
    public VoteCommentUpdateResponse updateVoteComment(Long voteId, Long voteCommentId, VoteCommentUpdateRequest req) {
        log.info("Update Vote Comment Service 진입");

        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        log.info("로그인 확인 완료! 유저 닉네임: "+member.getNickname());

        // 댓글이 작성된 게시글이 맞는지 확인
        Vote vote = voteRepository.findByVoteId(voteId).orElseThrow(VoteIdNotFoundException::new);

        // 댓글 존재여부 확인
        VoteComment voteComment = voteCommentRepository.findById(voteCommentId).orElseThrow(VoteCommentIdNotFoundException::new);

        // 댓글 작성자가 맞는지 확인
        memberRepository.findById(voteComment.getMember().getMemberId()).orElseThrow(MemberNotExistException::new);

        if (req.getContent() != null){
            voteComment.updateContent(req.getContent());
        }

        voteCommentRepository.save(voteComment);
        log.info("Update Vote Comment Service 종료");

        return VoteCommentUpdateResponse.from(voteComment);
    }

    // 자식 댓글 등록 (대댓글)
    private VoteComment createVoteChildComments(VoteCommentCreateRequest req, Member member, Vote vote) {
        VoteComment parent = voteCommentRepository.findById(req.getParentId()).orElseThrow(VoteCommentIdNotFoundException::new);

        return VoteComment.builder()
                .member(member)
                .vote(vote)
                .isDeleted(false)
                .content(req.getContent())
                .parent(parent)
                .build();
    }

    // 부모 댓글 등록 (댓글)
    private VoteComment createVoteParentComments(VoteCommentCreateRequest req, Member member, Vote vote) {

        return VoteComment.builder()
                .member(member)
                .vote(vote)
                .isDeleted(false)
                .content(req.getContent())
                .build();
    }

    // 투표해요 게시글 댓글 좋아요
    @Override
    @Transactional
    public String updateLikeOfVoteComment(Long voteId, Long voteCommentId) {
        log.info("Update Like Of VoteComment Service 진입");
        Member member = memberRepository.findById(securityUtils.getLoggedInMember()
                .orElseThrow(MemberNotLoginException::new)
                .getMemberId()).get();
        voteRepository.findById(voteId).orElseThrow(VoteIdNotFoundException::new);
        VoteComment voteComment = voteCommentRepository.findById(voteCommentId).orElseThrow(VoteCommentIdNotFoundException::new);

        if (!hasLikeVoteComment(voteComment, member)){
            voteComment.increaseLikes();
            return createLikeVoteComment(voteComment, member);
        }
        voteComment.decreaseLikes();

        return removeLikeVoteComment(voteComment, member);
    }

    private boolean hasLikeVoteComment(VoteComment voteComment, Member member){
        return voteCommentLikeRepository.findByVoteCommentAndMember(voteComment, member).isPresent();
    }

    private String createLikeVoteComment(VoteComment voteComment, Member member) {
        VoteCommentLike voteCommentLike = new VoteCommentLike(voteComment, member);
        voteCommentLikeRepository.save(voteCommentLike);
        log.info("댓글 좋아요 완료");

        return SUCCESS_LIKE_COMMENT;
    }

    private String removeLikeVoteComment(VoteComment voteComment, Member member) {
        VoteCommentLike voteCommentLike = voteCommentLikeRepository.findByVoteCommentAndMember(voteComment, member)
                .orElseThrow(VoteCommentLikeHistoryNotFoundException::new);
        voteCommentLikeRepository.delete(voteCommentLike);
        log.info("댓글 좋아요 취소 완료");

        return SUCCESS_UNLIKE_COMMENT;
    }
}
