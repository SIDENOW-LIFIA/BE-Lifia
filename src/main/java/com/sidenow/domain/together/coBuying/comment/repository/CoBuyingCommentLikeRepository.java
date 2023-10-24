package com.sidenow.domain.together.coBuying.comment.repository;

import com.sidenow.domain.together.coBuying.comment.entity.CoBuyingComment;
import com.sidenow.domain.together.coBuying.comment.entity.CoBuyingCommentLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoBuyingCommentLikeRepository extends JpaRepository<CoBuyingCommentLike, Long> {
    Optional<CoBuyingCommentLike> findByCoBuyingCommentAndMember(CoBuyingComment coBuyingComment, Member member);
}
