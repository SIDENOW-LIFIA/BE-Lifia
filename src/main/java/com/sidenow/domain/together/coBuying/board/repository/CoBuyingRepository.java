package com.sidenow.domain.together.coBuying.board.repository;

import com.sidenow.domain.together.coBuying.board.entity.CoBuying;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoBuyingRepository extends JpaRepository<CoBuying, Long> {
    Optional<CoBuying> findByCoBuyingId(Long coBuyingId);
    Page<CoBuying> findByOrderByCreatedAtDesc(Pageable pageable);
}
