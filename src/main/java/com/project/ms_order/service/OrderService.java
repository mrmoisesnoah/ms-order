package com.project.ms_order.service;


import com.project.ms_order.exceptions.BusinessRulesException;
import com.project.ms_order.exceptions.DataBaseException;
import com.project.ms_order.model.dto.OrderDTO;
import com.project.ms_order.model.dto.PageDTO;

public interface OrderService {

    PageDTO<OrderDTO> listPaginated(Integer page, Integer size) throws BusinessRulesException;

    OrderDTO getOrderById(Long id) throws DataBaseException;

}
