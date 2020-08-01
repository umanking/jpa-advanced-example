package com.example.demo.api;

import com.example.demo.domain.Order;
import com.example.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @since 2020-08-02
 * <p>
 * <p>
 * XtoMany 예제
 */
@RestController
@RequiredArgsConstructor
public class OrderApi {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> findOrdersV1() {
        List<Order> all = orderRepository.findAll();
        for (Order order : all) {
            order.getMember().getName();
            order.getOrderItems().forEach(o -> o.getBook().getName());
        }

        return all;
    }
}
