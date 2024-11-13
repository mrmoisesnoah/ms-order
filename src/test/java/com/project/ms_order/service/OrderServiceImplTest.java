package com.project.ms_order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ms_order.exceptions.BusinessRulesException;
import com.project.ms_order.exceptions.DataBaseException;
import com.project.ms_order.factory.OrdersFactory;
import com.project.ms_order.model.dto.OrderDTO;
import com.project.ms_order.model.dto.PageDTO;
import com.project.ms_order.model.entities.OrdersEntity;
import com.project.ms_order.repository.OrderRepository;
import com.project.ms_order.service.impl.OrderServiceImp;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        orderService = new OrderServiceImp(repository, objectMapper);
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
    public void testGetOrderByIdSuccess() throws DataBaseException {
        OrdersEntity order = OrdersFactory.getOrdersEntity();

        when(repository.findByOrdersId(1L)).thenReturn(Optional.of(order));

        OrderDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(order.getOrdersId(), result.getOrdersId());
        verify(repository, times(1)).findByOrdersId(1L);
    }

    @Test(expected = DataBaseException.class)
    public void testGetOrderByIdNotFound() throws DataBaseException {
        when(repository.findByOrdersId(1L)).thenReturn(Optional.empty());

        orderService.getOrderById(1L);
    }

    @Test
    public void testCreateOrderSuccess() throws JsonProcessingException {
        OrderDTO orderDTO = OrdersFactory.getOrderDTO();
        OrdersEntity order = OrdersFactory.getOrdersEntity();

        when(repository.save(any(OrdersEntity.class))).thenReturn(order);

        OrderDTO result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        assertEquals(orderDTO.getOrdersId(), result.getOrdersId());
        verify(repository, times(1)).save(any(OrdersEntity.class));
    }

    @Test
    public void testProcessOrderExists() {
        OrderDTO orderDTO = OrdersFactory.getOrderDTO();
        OrdersEntity orderEntity = OrdersFactory.getOrdersEntity();

        when(repository.existsByOrdersId(orderDTO.getOrdersId())).thenReturn(true);
        when(repository.findByOrdersId(orderDTO.getOrdersId())).thenReturn(Optional.of(orderEntity));

        orderService.processOrder(orderDTO);

        verify(repository, times(1)).existsByOrdersId(orderDTO.getOrdersId());
        verify(repository, times(1)).findByOrdersId(orderDTO.getOrdersId());
    }

    @Test
    public void testProcessOrderNotExists() {
        OrderDTO orderDTO = OrdersFactory.getOrderDTO();
        OrdersEntity order = OrdersFactory.getOrdersEntity();

        when(repository.existsByOrdersId(orderDTO.getOrdersId())).thenReturn(false);
        when(repository.save(any(OrdersEntity.class))).thenReturn(order);

        orderService.processOrder(orderDTO);

        verify(repository, times(1)).existsByOrdersId(orderDTO.getOrdersId());
        verify(repository, times(1)).save(any(OrdersEntity.class));
    }

    @Test
    public void testUpdateOrderSuccess() throws JsonProcessingException {
        OrdersEntity order = OrdersFactory.getOrdersEntity();
        OrderDTO orderDTO = OrdersFactory.getOrderDTO();

        when(repository.findByOrdersId(order.getOrdersId())).thenReturn(Optional.of(order));
        when(repository.save(any(OrdersEntity.class))).thenReturn(order);

        OrderDTO result = orderService.updateOrder(order.getOrdersId(), orderDTO);

        assertNotNull(result);
        assertEquals(orderDTO.getOrdersId(), result.getOrdersId());
        verify(repository, times(1)).findByOrdersId(order.getOrdersId());
        verify(repository, times(1)).save(any(OrdersEntity.class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateOrderNotFound() {
        OrderDTO orderDTO = OrdersFactory.getOrderDTO();

        when(repository.findByOrdersId(orderDTO.getOrdersId())).thenReturn(Optional.empty());

        orderService.updateOrder(orderDTO.getOrdersId(), orderDTO);
    }
}
