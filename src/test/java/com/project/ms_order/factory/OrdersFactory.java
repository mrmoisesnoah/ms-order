package com.project.ms_order.factory;

import com.project.ms_order.model.dto.OrderDTO;
import com.project.ms_order.model.entities.OrdersEntity;
import com.project.ms_order.model.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public class OrdersFactory {

    public static OrdersEntity getOrdersEntity() {
        return OrdersEntity.builder()
                .ordersId(1L)
                .dateTime(LocalDateTime.now())
                .status(Status.PLACED)
                .totalAmount(100.0)
                .items(List.of(ItemFactory.getItemEntity()))
                .build();
    }

    public static OrderDTO getOrderDTO() {
        return OrderDTO.builder()
                .ordersId(1L)
                .dateTime(LocalDateTime.now())
                .status(Status.PAID)
                .totalAmount(100.0)
                .items(List.of(ItemFactory.getItemDTO()))
                .build();
    }
}
