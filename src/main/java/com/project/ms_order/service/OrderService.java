package com.project.ms_order.service;


import com.project.ms_order.dto.OrderDTO;
import com.project.ms_order.dto.PageDTO;
import com.project.ms_order.exceptions.BusinessRulesException;

public interface OrderService {

    PageDTO<OrderDTO> listPaginated(Integer page, Integer size) throws BusinessRulesException;

    OrderDTO getOrderById(Long id);

}
