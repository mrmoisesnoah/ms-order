package com.project.ms_order.model.entities;

import com.project.ms_order.model.dto.ItemDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Integer quantity;

    private String description;

    private Double price;

    @ManyToOne(optional=true)
    private OrdersEntity orders;

    public static ItemEntity fromDTOtoEntity(ItemDTO item) {
        return ItemEntity.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .description(item.getDescription())
                .price(item.getPrice())
                .build();
    }

    public static ItemDTO fromEntityToDTO(ItemEntity item) {
        return ItemDTO.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .description(item.getDescription())
                .price(item.getPrice())
                .build();
    }
}
