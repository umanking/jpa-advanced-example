package com.example.demo.api;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderStatus;
import com.example.demo.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @since 2020-07-29
 */
@RestController
@RequiredArgsConstructor
public class SimpleOrderApi {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-order")
    public List<Order> findOrderV1() {
        // 1. 양방향 연관 관계 문제 -> @JsonIgnore
        // 2. proxy객체로 생성, ByteBuddyInterceptException 발생 -> Hibernate 5 moudle 로 설정
        return orderRepository.findAll();
    }

    @GetMapping("/api/v2/simple-order")
    public List<SimpleOrderDto> findOrderV2() {
        final List<Order> all = orderRepository.findAll();
        final List<SimpleOrderDto> collect = all.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());

        return collect;
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private OrderStatus orderStatus;
        private LocalDateTime orderDate;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderStatus = order.getOrderStatus();
            this.orderDate = order.getOrderDate();
        }
    }
}
