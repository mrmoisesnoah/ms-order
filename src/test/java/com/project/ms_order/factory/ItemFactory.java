package com.project.ms_order.factory;

import com.project.ms_order.model.dto.ItemDTO;
import com.project.ms_order.model.entities.ItemEntity;

public class ItemFactory {

    public static ItemEntity getItemEntity() {
        return ItemEntity.builder()
                .productId(1L)
                .quantity(2)
                .description("Test Item")
                .price(50.0)
                .build();
    }

    public static ItemDTO getItemDTO() {
        return ItemDTO.builder()
                .productId(1L)
                .quantity(2)
                .description("Test Item")
                .price(50.0)
                .build();
    }
}
