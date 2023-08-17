package com.sidenow.domain.boardType.free.comment.repository;

import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardComment, Long> {
}
