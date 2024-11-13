package com.project.ms_order.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.ms_order.model.dto.ItemDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Integer quantity;

    private String description;

    private Double price;

    @ManyToOne(optional = true)
    @JsonBackReference
    private OrdersEntity orders;

    public static ItemEntity fromDTOtoEntity(ItemDTO item) {
        return ItemEntity.builder()
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .description(item.getDescription())
                .price(item.getPrice())
                .build();
    }

    public static ItemDTO fromEntityToDTO(ItemEntity item) {
        return ItemDTO.builder()
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .description(item.getDescription())
                .price(item.getPrice())
                .build();
    }
}
