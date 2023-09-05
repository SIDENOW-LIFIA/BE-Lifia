package com.sidenow.domain.boardType.free.board.repository;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FreeBoardRepositoryCustom {

    Page<FreeBoard> findApartmentFreeBoard(String orderBy, Pageable pageable);

}
