package com.project.ms_order.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.ms_order.model.enums.Status;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDTO {

    private Long id;
    private LocalDateTime dateTime;
    private Status status;
    private Double totalAmount;
    private List<ItemDTO> items = new ArrayList<>();


}
