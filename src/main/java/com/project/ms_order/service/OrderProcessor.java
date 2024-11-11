package com.project.ms_order.service;

import com.project.ms_order.dto.OrderDTO;

public interface OrderProcessor {
    void processOrder(OrderDTO order);
}
