package com.sidenow.domain.together.delivery.board.repository;

import com.sidenow.domain.together.delivery.board.entity.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByDeliveryId(Long deliveryId);
    Page<Delivery> findByOrderByCreatedAtDesc(Pageable pageable);
}
