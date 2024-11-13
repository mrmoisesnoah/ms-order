package com.project.ms_order.model.dto;

import com.project.ms_order.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(description = "DTO representing an Order")
public class OrderDTO {

    @Schema(description = "Unique identifier of the order", example = "1")
    private Long id;

    @Schema(description = "Unique identifier of the customer who placed the order", example = "1001")
    private Long customerId;

    @Schema(description = "Date and time when the order was placed", example = "2024-11-13T15:30:02")
    private LocalDateTime dateTime;

    @Schema(description = "Current status of the order", allowableValues = {"PLACED", "CANCELED", "PAID", "NOT_AUTHORIZED", "CONFIRMED", "READY", "OUT_FOR_DELIVERY", "DELIVERED"})
    private Status status;

    @Schema(description = "Total amount of the order", example = "100.00")
    private Double totalAmount;

    @Schema(description = "List of items included in the order")
    private List<ItemDTO> items = new ArrayList<>();
}
