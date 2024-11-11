package com.project.ms_order.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.ms_order.model.Status;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    private Long id;
    private LocalDateTime dateTime;
    private Status status;
    private List<ItemDTO> items = new ArrayList<>();


}
