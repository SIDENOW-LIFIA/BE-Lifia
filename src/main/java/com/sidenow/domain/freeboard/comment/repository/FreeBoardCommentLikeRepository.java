package com.sidenow.domain.freeboard.comment.repository;

import com.sidenow.domain.freeboard.comment.entity.FreeBoardComment;
import com.sidenow.domain.freeboard.comment.entity.FreeBoardCommentLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeBoardCommentLikeRepository extends JpaRepository<FreeBoardCommentLike, Long> {
    Optional<FreeBoardCommentLike> findByFreeBoardCommentAndMember(FreeBoardComment freeBoardComment, Member member);
}
