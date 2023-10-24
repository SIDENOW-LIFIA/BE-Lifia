package com.sidenow.domain.together.coBuying.board.repository;

import com.sidenow.domain.together.coBuying.board.entity.CoBuying;
import com.sidenow.domain.together.coBuying.board.entity.CoBuyingLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoBuyingLikeRepository extends JpaRepository<CoBuyingLike, Long> {

    Optional<CoBuyingLike> findByCoBuyingAndMember(CoBuying coBuyingBoard, Member member);
}
