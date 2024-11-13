package com.project.ms_order.repository;

import com.project.ms_order.model.entities.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {

}
