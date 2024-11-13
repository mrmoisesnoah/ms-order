package com.project.ms_order.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {

    @Schema(description = "Unique identifier of the item", example = "1")
    private Long productId;

    @Schema(description = "Quantity of the item ordered", example = "2")
    private Integer quantity;

    @Schema(description = "Description of the item", example = "Wireless Mouse")
    private String description;

    @Schema(description = "Price per unit of the item", example = "25.50")
    private Double price;
}
