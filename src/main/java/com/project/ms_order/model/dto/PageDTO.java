package com.project.ms_order.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageDTO<T> {

    @Schema(description = "Total number of elements in the dataset", example = "100")
    private Long totalElements;

    @Schema(description = "Total number of pages available", example = "10")
    private Integer totalPages;

    @Schema(description = "Current page number", example = "0")
    private Integer page;

    @Schema(description = "Number of elements per page", example = "20")
    private Integer size;

    @Schema(description = "List of elements for the current page")
    private List<T> elements;
}
