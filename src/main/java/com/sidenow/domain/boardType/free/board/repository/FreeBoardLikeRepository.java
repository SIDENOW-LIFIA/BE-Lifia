package com.sidenow.domain.boardType.free.board.repository;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.entity.FreeBoardLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeBoardLikeRepository extends JpaRepository<FreeBoardLike, Long> {

    Optional<FreeBoardLike> findByFreeBoardAndMember(FreeBoard freeBoard, Member member);
}
