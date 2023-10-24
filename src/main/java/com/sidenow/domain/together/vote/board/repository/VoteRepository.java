package com.sidenow.domain.together.vote.board.repository;

import com.sidenow.domain.together.vote.board.entity.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByVoteId(Long voteId);
    Page<Vote> findByOrderByCreatedAtDesc(Pageable pageable);
}
