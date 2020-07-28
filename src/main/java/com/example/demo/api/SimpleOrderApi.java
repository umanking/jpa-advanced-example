package com.example.demo.api;

import com.example.demo.domain.Order;
import com.example.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @since 2020-07-29
 */
@RestController
@RequiredArgsConstructor
public class SimpleOrderApi {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-order")
    public List<Order> findOrderV1() {
        // 1. 양방향 연관관계 문제 -> @JsonIgnore
        // 2. proxy객체로 생성, ByteBuddyInterceptException 발생 -> Hibernate 5 moudle 로 설정
        return orderRepository.findAll();
    }
}
