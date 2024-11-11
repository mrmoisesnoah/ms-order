package com.project.ms_order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.project.ms_order.dto.ItemDTO;
import com.project.ms_order.dto.OrderDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private Double totalAmount;

    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="order")
    private List<Item> items = new ArrayList<>();

    public void updateFromDTO(OrderDTO orderDTO) {
        if (orderDTO.getDateTime() != null) {
            this.dateTime = orderDTO.getDateTime();
        }
        if (orderDTO.getStatus() != null) {
            this.status = orderDTO.getStatus();
        }
        if (orderDTO.getItems() != null) {
            this.items.clear();
            List<Item> itemsEntity = orderDTO.getItems().stream()
                .map(Item::fromDTOtoEntity)
                .collect(Collectors.toList());
            itemsEntity.forEach(item -> item.setOrder(this));
            this.items.addAll(itemsEntity);
            this.totalAmount = itemsEntity.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        }
    }

    public static Order fromDTOtoEntity(OrderDTO orderDTO) {
        Order order = Order.builder()
            .id(orderDTO.getId())
            .dateTime(orderDTO.getDateTime())
            .status(orderDTO.getStatus())
            .totalAmount(orderDTO.getItems().stream()
                .mapToDouble(itemDto -> itemDto.getPrice() * itemDto.getQuantity())
                .sum())
            .build();

        List<Item> itemsEntity = orderDTO.getItems().stream()
            .map(itemDto -> {
                Item itemEntity = Item.fromDTOtoEntity(itemDto);
                itemEntity.setOrder(order);
                return itemEntity;
            })
            .collect(Collectors.toList());

        order.setItems(itemsEntity);
        return order;
    }

    public static OrderDTO fromEntityToDTO(Order order) {
        OrderDTO orderDTO = OrderDTO.builder()
            .id(order.getId())
            .dateTime(order.getDateTime())
            .status(order.getStatus())
            .items(order.getItems().stream()
                .map(Item::fromEntityToDTO)
                .collect(Collectors.toList()))
            .build();

        return orderDTO;
    }


}
