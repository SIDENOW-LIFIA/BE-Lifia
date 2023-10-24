package com.sidenow.domain.boardType.coBuying.board.repository;

import com.sidenow.domain.boardType.coBuying.board.entity.CoBuying;
import com.sidenow.domain.boardType.coBuying.board.entity.CoBuyingLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoBuyingLikeRepository extends JpaRepository<CoBuyingLike, Long> {

    Optional<CoBuyingLike> findByCoBuyingAndMember(CoBuying coBuyingBoard, Member member);
}
