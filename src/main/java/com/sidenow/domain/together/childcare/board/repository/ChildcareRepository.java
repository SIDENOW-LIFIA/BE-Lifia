package com.sidenow.domain.together.childcare.board.repository;

import com.sidenow.domain.together.childcare.board.entity.Childcare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildcareRepository extends JpaRepository<Childcare, Long> {
    Optional<Childcare> findByChildcareId(Long childcareId);
    Page<Childcare> findByOrderByCreatedAtDesc(Pageable pageable);
}
