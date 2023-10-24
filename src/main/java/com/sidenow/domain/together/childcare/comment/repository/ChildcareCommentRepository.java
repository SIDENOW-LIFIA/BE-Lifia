package com.sidenow.domain.together.childcare.comment.repository;

import com.sidenow.domain.together.childcare.comment.entity.ChildcareComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildcareCommentRepository extends JpaRepository<ChildcareComment, Long> {
    List<ChildcareComment> findAllByChildcare_ChildcareIdOrderByCreatedAtAsc(Long childcareId);
}
