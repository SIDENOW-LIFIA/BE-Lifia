package com.sidenow.domain.boardType.childCare.board.repository;

import com.sidenow.domain.boardType.childCare.board.entity.ChildCareBoard;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildCareBoardRepository extends JpaRepository<ChildCareBoard, Long> {
    Optional<ChildCareBoard> findByChildCareBoardPostId(Long childCareBoardPostId);
    Page<ChildCareBoard> findByOrderByRegDateDesc(Pageable pageable);
}
