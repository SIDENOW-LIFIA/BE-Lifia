package com.sidenow.domain.freeboard.board.repository;

import com.sidenow.domain.freeboard.board.entity.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    Optional<FreeBoard> findByFreeBoardId(Long freeBoardId);
    Page<FreeBoard> findByOrderByCreatedAtDesc(Pageable pageable);
}
