package com.sidenow.domain.together.childcare.comment.repository;

import com.sidenow.domain.together.childcare.comment.entity.ChildcareComment;
import com.sidenow.domain.together.childcare.comment.entity.ChildcareCommentLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildcareCommentLikeRepository extends JpaRepository<ChildcareCommentLike, Long> {
    Optional<ChildcareCommentLike> findByChildcareCommentAndMember(ChildcareComment childcareComment, Member member);
}
