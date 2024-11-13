package com.project.ms_order.repository;

import com.project.ms_order.model.entities.OrdersEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {

    Optional<OrdersEntity> findByOrdersId(Long orderId);

    boolean existsByOrdersId(Long ordersId);
}
