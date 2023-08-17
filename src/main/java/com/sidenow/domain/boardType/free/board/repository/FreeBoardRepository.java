package com.sidenow.domain.boardType.free.board.repository;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    Optional<FreeBoard> findByPostId(Long postId);
    List<FreeBoard> findAllByMember(Member member);
    List<FreeBoard> findAllByTitle(String title);
}
