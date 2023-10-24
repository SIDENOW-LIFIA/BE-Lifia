package com.sidenow.domain.boardType.coBuying.comment.repository;

import com.sidenow.domain.boardType.coBuying.comment.entity.CoBuyingComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoBuyingCommentRepository extends JpaRepository<CoBuyingComment, Long> {
    List<CoBuyingComment> findAllByCoBuying_CoBuyingIdOrderByCreatedAtAsc(Long coBuyingId);
}
