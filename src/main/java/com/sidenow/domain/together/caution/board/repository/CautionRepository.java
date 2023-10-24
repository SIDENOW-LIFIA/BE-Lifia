package com.sidenow.domain.together.caution.board.repository;

import com.sidenow.domain.together.caution.board.entity.Caution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CautionRepository extends JpaRepository<Caution, Long> {
    Optional<Caution> findByCautionId(Long cautionId);
    Page<Caution> findByOrderByCreatedAtDesc(Pageable pageable);
}
