package com.sidenow.domain.boardType.caution.comment.repository;

import com.sidenow.domain.boardType.caution.comment.entity.CautionComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CautionCommentRepository extends JpaRepository<CautionComment, Long> {
    List<CautionComment> findAllByCaution_CautionIdOrderByCreatedAtAsc(Long cautionId);
}
