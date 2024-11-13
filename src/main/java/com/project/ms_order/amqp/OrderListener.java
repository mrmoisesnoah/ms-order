package com.project.ms_order.amqp;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ms_order.model.dto.OrderDTO;
import com.project.ms_order.service.OrderProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderListener {

    private final OrderProcessor orderProcessor;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "order.details-requests")
    public void message(@Payload OrderDTO order) throws JsonProcessingException {
        if (order == null) {
            log.error("Received null order message");
            throw new IllegalArgumentException("Order cannot be null");
        }

        log.info("Received message: {}", objectMapper.writeValueAsString(order));
        orderProcessor.processOrder(order);
    }

}
