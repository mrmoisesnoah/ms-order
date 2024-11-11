package com.project.ms_order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageDTO<T> {
    private Long totalElements;
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private List<T> elements;
}
