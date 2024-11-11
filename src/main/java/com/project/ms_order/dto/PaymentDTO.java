package com.project.ms_order.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import com.project.ms_order.model.StatusPayment;

@Getter
@Setter
@Data
@Builder
public class PaymentDTO {
    private Long id;
    private BigDecimal amount;
    private String name;
    private String number;
    private String expirationDate;
    private String code;
    private StatusPayment status;
    private Long paymentMethodId;
    private Long orderId;
}
