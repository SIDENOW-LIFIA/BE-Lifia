package com.sidenow.domain.boardType.delivery.comment.repository;

import com.sidenow.domain.boardType.delivery.comment.entity.DeliveryComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryCommentRepository extends JpaRepository<DeliveryComment, Long> {
    List<DeliveryComment> findAllByDelivery_DeliveryIdOrderByCreatedAtAsc(Long deliveryId);
}
