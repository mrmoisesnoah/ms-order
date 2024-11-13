package com.project.ms_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.project.ms_order.controller.documentation.OrderControllerDoc;
import com.project.ms_order.exceptions.BusinessRulesException;
import com.project.ms_order.model.dto.OrderDTO;
import com.project.ms_order.model.dto.PageDTO;
import com.project.ms_order.service.OrderService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/orders")
public class OrderController implements OrderControllerDoc {

        @Autowired
        private OrderService service;

        @GetMapping()
        public PageDTO<OrderDTO> listPaginated(Integer page, Integer size) {
            try {
                return service.listPaginated(page, size);
            } catch (BusinessRulesException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
        }
        
        @GetMapping("/{id}")
        public ResponseEntity<OrderDTO> getById(@PathVariable @NotNull Long id) {
            OrderDTO dto = service.getOrderById(id);
            return ResponseEntity.ok(dto);
        }
        
}
