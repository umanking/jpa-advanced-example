package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @since 2020-07-29
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Book book;

    private int orderPirce;
    private int count;

    public static OrderItem createOrderItem(Book book, int orderPrice, int count) {
        final OrderItem orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setOrderPirce(orderPrice);
        orderItem.setCount(count);

//        book.removeStock(count);
        return orderItem;
    }

}
