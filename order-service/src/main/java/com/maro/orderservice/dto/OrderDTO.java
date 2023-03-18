package com.maro.orderservice.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;
}
