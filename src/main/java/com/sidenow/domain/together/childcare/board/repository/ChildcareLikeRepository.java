package com.sidenow.domain.together.childcare.board.repository;

import com.sidenow.domain.together.childcare.board.entity.Childcare;
import com.sidenow.domain.together.childcare.board.entity.ChildcareLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildcareLikeRepository extends JpaRepository<ChildcareLike, Long> {

    Optional<ChildcareLike> findByChildcareAndMember(Childcare childcareBoard, Member member);
}
