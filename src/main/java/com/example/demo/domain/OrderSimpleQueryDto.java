package com.example.demo.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Geonguk Han
 * @since 2020-07-31
 */
@Data
public class OrderSimpleQueryDto {

    private Long orderId;
    private String name;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;

    public OrderSimpleQueryDto(Long orderId, String name, OrderStatus orderStatus, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.name = name;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }
}
