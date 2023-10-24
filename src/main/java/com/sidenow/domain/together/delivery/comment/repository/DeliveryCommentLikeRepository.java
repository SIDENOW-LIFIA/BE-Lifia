package com.sidenow.domain.together.delivery.comment.repository;

import com.sidenow.domain.together.delivery.comment.entity.DeliveryComment;
import com.sidenow.domain.together.delivery.comment.entity.DeliveryCommentLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryCommentLikeRepository extends JpaRepository<DeliveryCommentLike, Long> {
    Optional<DeliveryCommentLike> findByDeliveryCommentAndMember(DeliveryComment deliveryComment, Member member);
}
