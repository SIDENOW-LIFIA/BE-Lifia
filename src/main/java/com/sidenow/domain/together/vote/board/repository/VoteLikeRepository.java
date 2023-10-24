package com.sidenow.domain.together.vote.board.repository;

import com.sidenow.domain.together.vote.board.entity.Vote;
import com.sidenow.domain.together.vote.board.entity.VoteLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteLikeRepository extends JpaRepository<VoteLike, Long> {

    Optional<VoteLike> findByVoteAndMember(Vote voteBoard, Member member);
}
