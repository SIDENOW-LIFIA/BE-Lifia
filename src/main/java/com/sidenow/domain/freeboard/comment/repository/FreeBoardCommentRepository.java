package com.sidenow.domain.freeboard.comment.repository;

import com.sidenow.domain.freeboard.comment.entity.FreeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardComment, Long> {
    List<FreeBoardComment> findAllByFreeBoard_FreeBoardIdOrderByCreatedAtAsc(Long freeBoardId);
}
