package com.sidenow.domain.boardType.caution.comment.repository;

import com.sidenow.domain.boardType.caution.comment.entity.CautionComment;
import com.sidenow.domain.boardType.caution.comment.entity.CautionCommentLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CautionCommentLikeRepository extends JpaRepository<CautionCommentLike, Long> {
    Optional<CautionCommentLike> findByCautionCommentAndMember(CautionComment cautionComment, Member member);
}
