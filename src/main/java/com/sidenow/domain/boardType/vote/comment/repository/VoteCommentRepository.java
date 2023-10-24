package com.sidenow.domain.boardType.vote.comment.repository;

import com.sidenow.domain.boardType.vote.comment.entity.VoteComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteCommentRepository extends JpaRepository<VoteComment, Long> {
    List<VoteComment> findAllByVote_VoteIdOrderByCreatedAtAsc(Long voteId);
}
