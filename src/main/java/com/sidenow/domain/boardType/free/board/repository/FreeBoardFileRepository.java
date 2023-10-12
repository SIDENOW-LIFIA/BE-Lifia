package com.sidenow.domain.boardType.free.board.repository;

import com.sidenow.domain.boardType.free.board.dto.res.FreeBoardResponse;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import com.sidenow.domain.boardType.free.board.entity.FreeBoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeBoardFileRepository extends JpaRepository<FreeBoardFile, Long> {
    List<FreeBoardFile> findByFreeBoard(FreeBoard freeBoard);
}
