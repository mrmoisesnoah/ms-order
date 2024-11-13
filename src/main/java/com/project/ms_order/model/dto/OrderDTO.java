package com.project.ms_order.model.dto;

import com.project.ms_order.model.enums.Status;
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
public class OrderDTO {

    private Long id;
    private Long customerId;
    private LocalDateTime dateTime;
    private Status status;
    private Double totalAmount;
    private List<ItemDTO> items = new ArrayList<>();


}
