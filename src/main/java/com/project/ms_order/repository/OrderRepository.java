package com.project.ms_order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ms_order.model.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
