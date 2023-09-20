package com.sidenow.domain.boardType.childCare.comment.repository;

import com.sidenow.domain.boardType.childCare.comment.entity.ChildCareBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildCareBoardCommentRepository extends JpaRepository<ChildCareBoardComment, Long> {
    List<ChildCareBoardComment> findAllByChildCareBoard_ChildCareBoardPostIdOrderByCreatedAtAsc(Long childCareBoardPostId);
}
