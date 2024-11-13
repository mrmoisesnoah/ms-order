package com.project.ms_order.service;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ms_order.exceptions.BusinessRulesException;
import com.project.ms_order.factory.OrdersFactory;
import com.project.ms_order.model.dto.OrderDTO;
import com.project.ms_order.model.dto.PageDTO;
import com.project.ms_order.model.entities.OrdersEntity;
import com.project.ms_order.repository.OrderRepository;
import com.project.ms_order.service.impl.OrderServiceImp;

import jakarta.persistence.EntityNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImp orderService;

    @Mock
    private OrderRepository repository;

    @Mock
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        orderService = new OrderServiceImp(repository, objectMapper, null);
    }

    @Test
    public void testListPaginatedSuccess() throws BusinessRulesException {
        int page = 0;
        int size = 5;
        OrdersEntity order = OrdersFactory.getOrdersEntity();
        PageImpl<OrdersEntity> pageResult = new PageImpl<>(List.of(order), PageRequest.of(page, size), 1);

        when(repository.findAll(any(Pageable.class))).thenReturn(pageResult);

        PageDTO<OrderDTO> result = orderService.listPaginated(page, size);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test(expected = BusinessRulesException.class)
    public void testListPaginatedWithInvalidSize() throws BusinessRulesException {
        orderService.listPaginated(-1, 5);
    }

    @Test
    public void testGetOrderByIdSuccess() {
        OrdersEntity order = OrdersFactory.getOrdersEntity();

        when(repository.findById(1L)).thenReturn(Optional.of(order));

        OrderDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetOrderByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        orderService.getOrderById(1L);
    }

    @Test
    public void testCreateOrderSuccess() throws JsonProcessingException {
        OrderDTO orderDTO = OrdersFactory.getOrderDTO();
        OrdersEntity order = OrdersFactory.getOrdersEntity();

        when(repository.save(any(OrdersEntity.class))).thenReturn(order);
        when(objectMapper.writeValueAsString(any(OrdersEntity.class))).thenReturn("Order JSON");

        OrderDTO result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        assertEquals(orderDTO.getId(), result.getId());
        verify(repository, times(1)).save(any(OrdersEntity.class));
    }

    @Test
    public void testProcessOrderExists() {
        OrderDTO orderDTO = OrdersFactory.getOrderDTO();

        when(repository.existsById(orderDTO.getId())).thenReturn(true);

        orderService.processOrder(orderDTO);

        verify(repository, times(1)).existsById(orderDTO.getId());
    }

    @Test
    public void testProcessOrderNotExists() {
        OrderDTO orderDTO = OrdersFactory.getOrderDTO();
        OrdersEntity order = OrdersFactory.getOrdersEntity();

        when(repository.existsById(orderDTO.getId())).thenReturn(false);
        when(repository.save(any(OrdersEntity.class))).thenReturn(order);

        orderService.processOrder(orderDTO);

        verify(repository, times(1)).existsById(orderDTO.getId());
        verify(repository, times(1)).save(any(OrdersEntity.class));
    }

    @Test
    public void testUpdateOrderSuccess() throws JsonProcessingException {
        OrdersEntity order = OrdersFactory.getOrdersEntity();
        OrderDTO orderDTO = OrdersFactory.getOrderDTO();

        when(repository.findById(order.getId())).thenReturn(Optional.of(order));
        when(repository.save(any(OrdersEntity.class))).thenReturn(order);
        when(objectMapper.writeValueAsString(any(OrdersEntity.class))).thenReturn("Order JSON");

        OrderDTO result = orderService.updateOrder(order.getId(), orderDTO);

        assertNotNull(result);
        assertEquals(orderDTO.getId(), result.getId());
        verify(repository, times(1)).findById(order.getId());
        verify(repository, times(1)).save(any(OrdersEntity.class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateOrderNotFound() {
        OrderDTO orderDTO = OrdersFactory.getOrderDTO();

        when(repository.findById(orderDTO.getId())).thenReturn(Optional.empty());

        orderService.updateOrder(orderDTO.getId(), orderDTO);
    }
}
