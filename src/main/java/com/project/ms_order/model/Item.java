package com.project.ms_order.model;

import com.project.ms_order.dto.ItemDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "item_do_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Integer quantity;

    private String description;

    private Double price;

    @ManyToOne(optional=false)
    private Order order;

    public static Item fromDTOtoEntity(ItemDTO item) {
        return Item.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .description(item.getDescription())
                .price(item.getPrice())
                .build();
    }

    public static ItemDTO fromEntityToDTO(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .description(item.getDescription())
                .price(item.getPrice())
                .build();
    }
}
