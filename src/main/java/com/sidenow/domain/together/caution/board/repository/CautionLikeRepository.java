package com.sidenow.domain.together.caution.board.repository;

import com.sidenow.domain.together.caution.board.entity.Caution;
import com.sidenow.domain.together.caution.board.entity.CautionLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CautionLikeRepository extends JpaRepository<CautionLike, Long> {

    Optional<CautionLike> findByCautionAndMember(Caution cautionBoard, Member member);
}
