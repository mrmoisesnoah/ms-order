package com.project.ms_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ms_order.model.entities.OrdersEntity;

public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {
    
}
