package com.project.ms_order.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ms_order.exceptions.BusinessRulesException;
import com.project.ms_order.exceptions.DataBaseException;
import com.project.ms_order.model.dto.OrderDTO;
import com.project.ms_order.model.dto.PageDTO;
import com.project.ms_order.model.entities.OrdersEntity;
import com.project.ms_order.repository.OrderRepository;
import com.project.ms_order.service.OrderProcessor;
import com.project.ms_order.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImp implements OrderService, OrderProcessor {

    private final OrderRepository repository;
    private final ObjectMapper objectMapper;


    public PageDTO<OrderDTO> listPaginated(Integer page, Integer size) throws BusinessRulesException {
        if (size < 0 || page < 0) {
            throw new BusinessRulesException("Page or Size cannot be less than zero.");
        }
        if (size > 0) {
            Page<OrdersEntity> pageRepository = repository.findAll(PageRequest.of(page, size));
            List<OrderDTO> personDTOList = pageRepository.stream().map(OrdersEntity::fromEntityToDTO).toList();

            return new PageDTO<>(pageRepository.getTotalElements(),
                    pageRepository.getTotalPages(),
                    page,
                    size,
                    personDTOList);
        }
        List<OrderDTO> emptyList = new ArrayList<>();
        return new PageDTO<>(0L, 0, 0, size, emptyList);
    }

    public OrderDTO getOrderById(Long id) throws DataBaseException {
        OrdersEntity order = repository.findByOrdersId(id)
                .orElseThrow(() -> new DataBaseException("Order " + id + " not found"));

        return OrdersEntity.fromEntityToDTO(order);
    }

    public OrderDTO createOrder(OrderDTO dto) {

        OrdersEntity newOrder = OrdersEntity.fromDTOtoEntity(dto);
        repository.save(newOrder);

        try {
            log.info("Saved Order: {}", objectMapper.writeValueAsString(newOrder));
        } catch (JsonProcessingException e) {
            log.error("Error converting Order to JSON", e);
        }

        return OrdersEntity.fromEntityToDTO(newOrder);
    }

    public void processOrder(OrderDTO order) {
        if (repository.existsByOrdersId(order.getOrdersId())) {
            updateOrder(order.getOrdersId(), order);
        } else {
            createOrder(order);
        }
    }


    public OrderDTO updateOrder(Long id, OrderDTO dto) {
        OrdersEntity order = repository.findByOrdersId(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderEntity with id " + id + " not found"));

        order.getItems().clear();

        order.updateFromDTO(dto);
        repository.save(order);
        try {
            log.info("Updated Order: {}", objectMapper.writeValueAsString(order));
        } catch (JsonProcessingException e) {
            log.error("Error converting Order to JSON", e);
        }

        return OrdersEntity.fromEntityToDTO(order);
    }

}