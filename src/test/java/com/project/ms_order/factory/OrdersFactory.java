package com.project.ms_order.factory;

import java.time.LocalDateTime;
import java.util.List;

import com.project.ms_order.model.dto.OrderDTO;
import com.project.ms_order.model.entities.OrdersEntity;
import com.project.ms_order.model.enums.Status;

public class OrdersFactory {

    public static OrdersEntity getOrdersEntity() {
        return OrdersEntity.builder()
            .id(1L)
            .dateTime(LocalDateTime.now())
            .status(Status.PLACED)
            .totalAmount(100.0)
            .items(List.of(ItemFactory.getItemEntity()))  
            .build();
    }

    public static OrderDTO getOrderDTO() {
        return OrderDTO.builder()
            .id(1L)
            .dateTime(LocalDateTime.now())
            .status(Status.PAID)
            .totalAmount(100.0)
            .items(List.of(ItemFactory.getItemDTO()))  
            .build();
    }
}
