package com.sidenow.domain.boardType.vote.board.repository;

import com.sidenow.domain.boardType.vote.board.dto.res.VoteBoardResponse;
import com.sidenow.domain.boardType.vote.board.entity.VoteBoard;
import com.sidenow.domain.boardType.vote.board.entity.VoteBoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteBoardFileRepository extends JpaRepository<VoteBoardFile, Long> {
    List<VoteBoardFile> findByVoteBoard(VoteBoard voteBoard);
}
