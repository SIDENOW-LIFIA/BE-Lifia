package com.sidenow.domain.boardType.free.board.repository;

import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.entity.FreeBoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardFileRepository extends JpaRepository<FreeBoardFile, Long> {
    List<FreeBoardFile> findByFreeBoard(FreeBoard freeBoard);
}
