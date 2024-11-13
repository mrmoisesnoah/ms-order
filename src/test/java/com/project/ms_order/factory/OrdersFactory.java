package com.project.ms_order.factory;

import com.project.ms_order.model.dto.ItemDTO;
import com.project.ms_order.model.dto.OrderDTO;
import com.project.ms_order.model.entities.ItemEntity;
import com.project.ms_order.model.entities.OrdersEntity;
import com.project.ms_order.model.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdersFactory {

    public static OrdersEntity getOrdersEntity() {
        List<ItemEntity> items = new ArrayList<>();
        items.add(ItemFactory.getItemEntity());
        return OrdersEntity.builder()
                .ordersId(1L)
                .dateTime(LocalDateTime.now())
                .status(Status.PLACED)
                .totalAmount(100.0)
                .items(items)
                .build();
    }

    public static OrderDTO getOrderDTO() {
        List<ItemDTO> items = new ArrayList<>();
        items.add(ItemFactory.getItemDTO());
        return OrderDTO.builder()
                .ordersId(1L)
                .dateTime(LocalDateTime.now())
                .status(Status.PAID)
                .totalAmount(100.0)
                .items(items)
                .build();
    }
}
