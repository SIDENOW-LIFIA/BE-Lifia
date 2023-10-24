package com.sidenow.domain.together.delivery.board.repository;

import com.sidenow.domain.together.delivery.board.entity.Delivery;
import com.sidenow.domain.together.delivery.board.entity.DeliveryLike;
import com.sidenow.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryLikeRepository extends JpaRepository<DeliveryLike, Long> {

    Optional<DeliveryLike> findByDeliveryAndMember(Delivery deliveryBoard, Member member);
}
