package com.project.ms_order.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ms_order.amqp.OrderListener;
import com.project.ms_order.factory.OrdersFactory;
import com.project.ms_order.model.dto.OrderDTO;

@ExtendWith(MockitoExtension.class)
public class OrderListenerTest {

    @InjectMocks
    private OrderListener orderListener;

    @Mock
    private OrderProcessor orderProcessor;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Logger log;

    private OrderDTO order;


    @Test
    public void testMessageProcessingSuccess() throws JsonProcessingException {

        when(objectMapper.writeValueAsString(OrdersFactory.getOrderDTO())).thenReturn("Mocked Order JSON");


        orderListener.message(order);


        verify(orderProcessor, times(1)).processOrder(order);


        verify(log, times(1)).info("Received message Mocked Order JSON");
    }

    @Test
    public void testMessageProcessingWithJsonException() throws JsonProcessingException {

        when(objectMapper.writeValueAsString(OrdersFactory.getOrderDTO())).thenThrow(new JsonProcessingException("Mocked JSON Exception") {});

  
        orderListener.message(order);


        verify(orderProcessor, times(1)).processOrder(order);


        verify(log, times(1)).info("Received message ");
    }
}
