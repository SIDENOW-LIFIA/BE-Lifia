package com.sidenow.domain.boardType.free.board.repository;

import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.entity.FreeBoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeBoardImageRepository extends JpaRepository<FreeBoardImage, Long> {
    List<FreeBoardImage> findByFreeBoard(FreeBoard freeBoard);
}
