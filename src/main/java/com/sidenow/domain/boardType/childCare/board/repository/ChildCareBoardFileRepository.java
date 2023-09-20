package com.sidenow.domain.boardType.childCare.board.repository;

import com.sidenow.domain.boardType.childCare.board.dto.res.ChildCareBoardResponse;
import com.sidenow.domain.boardType.childCare.board.entity.ChildCareBoard;
import com.sidenow.domain.boardType.childCare.board.entity.ChildCareBoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildCareBoardFileRepository extends JpaRepository<ChildCareBoardFile, Long> {
    List<ChildCareBoardFile> findByChildCareBoard(ChildCareBoard childCareBoard);
}
