package com.project.ms_order.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.ms_order.model.dto.OrderDTO;
import com.project.ms_order.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private Long ordersId;

    @NotNull
    private Long customerId;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private Double totalAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orders", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ItemEntity> items = new ArrayList<>();

    public static OrdersEntity fromDTOtoEntity(OrderDTO orderDTO) {
        OrdersEntity order = OrdersEntity.builder()
                .ordersId(orderDTO.getOrdersId())
                .customerId(orderDTO.getCustomerId())
                .dateTime(orderDTO.getDateTime())
                .status(orderDTO.getStatus())
                .build();

        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            List<ItemEntity> itemsEntity = orderDTO.getItems().stream()
                    .map(ItemEntity::fromDTOtoEntity)
                    .collect(Collectors.toList());

            order.setItems(itemsEntity);
            itemsEntity.forEach(item -> item.setOrders(order));

            order.setTotalAmount(itemsEntity.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum());
        } else {
            order.setTotalAmount(0.0);
        }

        return order;
    }


    public static OrderDTO fromEntityToDTO(OrdersEntity order) {

        return OrderDTO.builder()
                .ordersId(order.getOrdersId())
                .customerId(order.getCustomerId())
                .dateTime(order.getDateTime())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(order.getItems().stream()
                        .map(ItemEntity::fromEntityToDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public void updateFromDTO(OrderDTO orderDTO) {
        if (orderDTO.getDateTime() != null) {
            this.dateTime = orderDTO.getDateTime();
        }
        if (orderDTO.getStatus() != null) {
            this.status = orderDTO.getStatus();
        }
        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            this.items = orderDTO.getItems().stream()
                    .map(ItemEntity::fromDTOtoEntity)
                    .collect(Collectors.toList());
            this.items.forEach(item -> item.setOrders(this));

            this.totalAmount = this.items.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();
        }
    }

}
