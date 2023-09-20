package com.sidenow.domain.boardType.childCare.comment.service;

import com.sidenow.domain.boardType.childCare.board.entity.ChildCareBoard;
import com.sidenow.domain.boardType.childCare.board.exception.NotFoundChildCareBoardPostIdException;
import com.sidenow.domain.boardType.childCare.board.repository.ChildCareBoardRepository;
import com.sidenow.domain.boardType.childCare.comment.dto.req.ChildCareBoardCommentRequest.RegisterChildCareBoardCommentRequest;
import com.sidenow.domain.boardType.childCare.comment.dto.res.ChildCareBoardCommentResponse.ChildCareBoardCommentCheck;
import com.sidenow.domain.boardType.childCare.comment.dto.res.ChildCareBoardCommentResponse.ChildCareBoardGetCommentListResponse;
import com.sidenow.domain.boardType.childCare.comment.entity.ChildCareBoardComment;
import com.sidenow.domain.boardType.childCare.comment.exception.ChildCareBoardCommentAuthErrorException;
import com.sidenow.domain.boardType.childCare.comment.exception.NotFoundChildCareBoardCommentIdException;
import com.sidenow.domain.boardType.childCare.comment.repository.ChildCareBoardCommentRepository;
import com.sidenow.domain.member.entity.Member;
import com.sidenow.domain.member.exception.MemberNotExistException;
import com.sidenow.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class ChildCareBoardCommentServiceImpl implements ChildCareBoardCommentService{

    private final MemberRepository memberRepository;
    private final ChildCareBoardRepository childCareBoardRepository;
    private final ChildCareBoardCommentRepository childCareBoardCommentRepository;

    @Override
    // 육아게시판 게시글 댓글 등록
    public ChildCareBoardCommentCheck registerChildCareBoardComment(Long childCareBoardPostId, RegisterChildCareBoardCommentRequest registerChildCareBoardCommentRequest) {
        log.info("Create ChildCareBoard Comment Service Start");
        ChildCareBoardCommentCheck childCareBoardCommentCheck = new ChildCareBoardCommentCheck();
        Member findMember = memberRepository.findById(registerChildCareBoardCommentRequest.getMemberId()).orElseThrow(MemberNotExistException::new);
        ChildCareBoard findChildCareBoardPost = childCareBoardRepository.findByChildCareBoardPostId(childCareBoardPostId).orElseThrow(NotFoundChildCareBoardPostIdException::new);
        ChildCareBoardComment childCareBoardComments;
        if (registerChildCareBoardCommentRequest.getParentId() == null) {
            childCareBoardComments = createChildCareBoardParentComments(registerChildCareBoardCommentRequest, findMember, findChildCareBoardPost);
        } else {
            childCareBoardComments = createChildCareBoardChildComments(registerChildCareBoardCommentRequest, findMember, findChildCareBoardPost);
        }
        childCareBoardCommentRepository.save(childCareBoardComments);
        childCareBoardCommentCheck.setSaved(true);
        log.info("Create ChildCareBoard Comment Service End");
        return childCareBoardCommentCheck;
    }

    @Override
    // 육아게시판 게시글의 댓글 전체 조회
    public List<ChildCareBoardGetCommentListResponse> getChildCareBoardCommentList(Long childCareBoardPostId) {
        log.info("Read ChildCareBoard Comment Service Start");
        ChildCareBoard findChildCareBoardPost = childCareBoardRepository.findByChildCareBoardPostId(childCareBoardPostId).orElseThrow(NotFoundChildCareBoardPostIdException::new);
        List<ChildCareBoardComment> childCareBoardCommentsList = childCareBoardCommentRepository.findAllByChildCareBoard_ChildCareBoardPostIdOrderByCreatedAtAsc(findChildCareBoardPost.getChildCareBoardPostId());
        List<ChildCareBoardGetCommentListResponse> readChildCareBoardCommentDto = new ArrayList<>();
        childCareBoardCommentsList.forEach(s -> readChildCareBoardCommentDto.add(ChildCareBoardGetCommentListResponse.from(s))); // 댓글 전체 조회 핵심 코드 (람다식 forEach 사용)
        log.info("Read ChildCareBoard Comment Service Start");
        return readChildCareBoardCommentDto;
    }

    @Override
    // 육아게시판 게시글의 댓글 삭제
    public ChildCareBoardCommentCheck deleteChildCareBoardComment(Long childCareBoardPostId, Long childCareBoardCommentId) {
        log.info("Delete ChildCareBoard Comment Service Start");
        ChildCareBoardCommentCheck childCareBoardCommentCheck = new ChildCareBoardCommentCheck();
        childCareBoardRepository.findByChildCareBoardPostId(childCareBoardPostId).orElseThrow(NotFoundChildCareBoardPostIdException::new);
        ChildCareBoardComment findChildCareBoardComment = childCareBoardCommentRepository.findById(childCareBoardCommentId).orElseThrow(NotFoundChildCareBoardCommentIdException::new);
        findChildCareBoardComment.changeIsDeleted(true);
        childCareBoardCommentRepository.deleteById(findChildCareBoardComment.getCommentId()); // 댓글 삭제
        childCareBoardCommentCheck.setDeleted(true);
        log.info("Delete ChildCareBoard Comment Service End");
        return childCareBoardCommentCheck;
    }

    @Override
    // 육아게시판 게시글의 댓글 수정
    public ChildCareBoardCommentCheck modifyChildCareBoardComment(Long childCareBoardPostId, Long childCareBoardCommentId, RegisterChildCareBoardCommentRequest registerChildCareBoardCommentRequest) {
        log.info("Modify ChildCareBoard Comment Service Start");
        ChildCareBoardCommentCheck childCareBoardCommentCheck = new ChildCareBoardCommentCheck();
        Member findMember = memberRepository.findById(registerChildCareBoardCommentRequest.getMemberId()).orElseThrow(MemberNotExistException::new);
        childCareBoardRepository.findByChildCareBoardPostId(childCareBoardPostId).orElseThrow(NotFoundChildCareBoardPostIdException::new);
        ChildCareBoardComment findChildCareBoardComment = childCareBoardCommentRepository.findById(childCareBoardCommentId).orElseThrow(NotFoundChildCareBoardCommentIdException::new);
        if (findChildCareBoardComment.getIsDeleted()) {
            throw new NotFoundChildCareBoardCommentIdException();
        }
        if (!findChildCareBoardComment.getMember().getMemberId().equals(findMember.getMemberId())){
            throw new ChildCareBoardCommentAuthErrorException();
        }
        ChildCareBoardComment newChildCareBoardComment = findChildCareBoardComment.updateContent(registerChildCareBoardCommentRequest.getContent());
        newChildCareBoardComment.setUpdatedAt(LocalDateTime.now());
        childCareBoardCommentRepository.save(newChildCareBoardComment);
        childCareBoardCommentCheck.setUpdated(true);
        log.info("Modify ChildCareBoard Comment Service End");
        return childCareBoardCommentCheck;
    }

    // 자식 댓글 등록 (대댓글)
    private ChildCareBoardComment createChildCareBoardChildComments(RegisterChildCareBoardCommentRequest createChildCareBoardCommentRequest, Member member, ChildCareBoard childCareBoard) {
        ChildCareBoardComment parent = childCareBoardCommentRepository.findById(createChildCareBoardCommentRequest.getParentId()).orElseThrow(NotFoundChildCareBoardCommentIdException::new);

        return ChildCareBoardComment.builder()
                .member(member)
                .regDate(LocalDateTime.now())
                .childCareBoard(childCareBoard)
                .isDeleted(false)
                .content(createChildCareBoardCommentRequest.getContent())
                .parent(parent)
                .build();
    }

    // 부모 댓글 등록 (댓글)
    private ChildCareBoardComment createChildCareBoardParentComments(RegisterChildCareBoardCommentRequest createChildCareBoardCommentRequest, Member member, ChildCareBoard childCareBoard) {

        return ChildCareBoardComment.builder()
                .member(member)
                .regDate(LocalDateTime.now())
                .childCareBoard(childCareBoard)
                .isDeleted(false)
                .content(createChildCareBoardCommentRequest.getContent())
                .build();
    }
}
