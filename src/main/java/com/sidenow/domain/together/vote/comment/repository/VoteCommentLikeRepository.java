package com.sidenow.domain.together.vote.comment.repository;

import com.sidenow.domain.together.vote.comment.entity.VoteComment;
import com.sidenow.domain.together.vote.comment.entity.VoteCommentLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteCommentLikeRepository extends JpaRepository<VoteCommentLike, Long> {
    Optional<VoteCommentLike> findByVoteCommentAndMember(VoteComment voteComment, Member member);
}
