package com.sidenow.domain.boardType.free.board.repository;

import com.fasterxml.jackson.core.SerializableString;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sidenow.domain.boardType.free.board.entity.FreeBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Slf4j
@Repository
public class FreeBoardRepositoryCustomImpl implements FreeBoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FreeBoard> findApartmentFreeBoard(String orderBy, Pageable pageable) {

        log.info("findApartmentFreeBoard Query 진입");
        log.info("findApartmentFreeBoard Query 종료");
    }


}
