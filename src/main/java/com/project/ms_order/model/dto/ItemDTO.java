package com.project.ms_order.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {

    private Long id;
    private Integer quantity;
    private String description;
    private Double price;
}
