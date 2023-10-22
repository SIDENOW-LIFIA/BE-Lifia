package com.sidenow.domain.boardType.free.comment.repository;

import com.sidenow.domain.boardType.free.comment.entity.FreeBoardComment;
import com.sidenow.domain.boardType.free.comment.entity.FreeBoardCommentLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeBoardCommentLikeRepository extends JpaRepository<FreeBoardCommentLike, Long> {
    Optional<FreeBoardCommentLike> findByFreeBoardCommentAndMember(FreeBoardComment freeBoardComment, Member member);
}
