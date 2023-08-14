package com.sidenow.domain.board.free.repository;

import com.sidenow.domain.board.free.entity.FreeBoard;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    FreeBoard findByPostId(Long postId);
    List<FreeBoard> findAllByWriter(Member writer);
    List<FreeBoard> findAllByTitle(String title);
}
