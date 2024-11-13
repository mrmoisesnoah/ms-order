package com.project.ms_order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ms_order.amqp.OrderListener;
import com.project.ms_order.factory.OrdersFactory;
import com.project.ms_order.model.dto.OrderDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderListenerTest {

    @InjectMocks
    private OrderListener orderListener;

    @Mock
    private OrderProcessor orderProcessor;

    @Mock
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        orderListener = new OrderListener(orderProcessor, objectMapper);
    }

    @Test
    public void testMessageProcessingSuccess() throws JsonProcessingException {
        OrderDTO order = OrdersFactory.getOrderDTO();

        orderListener.message(order);

        verify(orderProcessor, times(1)).processOrder(order);
    }

}
