package com.project.ms_order.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.project.ms_order.model.dto.OrderDTO;
import com.project.ms_order.model.enums.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private Double totalAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orders")
    private List<ItemEntity> items = new ArrayList<>();
    

    public void updateFromDTO(OrderDTO orderDTO) {
        if (orderDTO.getDateTime() != null) {
            this.dateTime = orderDTO.getDateTime();
        }
        if (orderDTO.getStatus() != null) {
            this.status = orderDTO.getStatus();
        }
        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            this.items.clear();
            List<ItemEntity> itemsEntity = orderDTO.getItems().stream()
                .map(ItemEntity::fromDTOtoEntity)
                .collect(Collectors.toList());
            itemsEntity.forEach(item -> item.setOrders(this));
            this.items.addAll(itemsEntity);
            this.totalAmount = itemsEntity.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        }
    }

    public static OrdersEntity fromDTOtoEntity(OrderDTO orderDTO) {
        OrdersEntity order = OrdersEntity.builder()
            .id(orderDTO.getId())
            .dateTime(orderDTO.getDateTime())
            .status(orderDTO.getStatus())
            .build();

        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            List<ItemEntity> itemsEntity = orderDTO.getItems().stream()
                .map(itemDto -> {
                    ItemEntity itemEntity = ItemEntity.fromDTOtoEntity(itemDto);
                    return itemEntity;
                })
                .collect(Collectors.toList());

            order.setItems(itemsEntity);
            order.setTotalAmount(itemsEntity.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum());
        } else {
            order.setTotalAmount(0.0);
        }

        return order;
    }

    public static OrderDTO fromEntityToDTO(OrdersEntity order) {
        OrderDTO orderDTO = OrderDTO.builder()
            .id(order.getId())
            .dateTime(order.getDateTime())
            .status(order.getStatus())
            .totalAmount(order.getTotalAmount()) // Atualiza o totalAmount no DTO
            .items(order.getItems().stream()
                .map(ItemEntity::fromEntityToDTO)
                .collect(Collectors.toList()))
            .build();

        return orderDTO;
    }
}
