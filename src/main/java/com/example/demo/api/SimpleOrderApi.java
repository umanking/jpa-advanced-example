package com.example.demo.api;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderSimpleQueryDto;
import com.example.demo.domain.OrderStatus;
import com.example.demo.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
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
    private final EntityManager em;

    /**
     * 1. 양방향 연관 관계 문제 -> @JsonIgnore
     * 2. proxy객체로 생성, ByteBuddyInterceptException 발생 -> Hibernate 5 moudle 로 설정
     *
     * @return
     */
    @GetMapping("/api/v1/simple-order")
    public List<Order> findOrderV1() {

        return orderRepository.findAll();
    }

    /**
     * Entity 가 아닌 Dto로 반환
     * 하지만 N+1 문제 발생
     * 1번의 쿼리(1)의 각각 lazy loading 건을 재조회 (N)
     *
     * @return
     */
    @GetMapping("/api/v2/simple-order")
    public List<SimpleOrderDto> findOrderV2() {
        final List<Order> all = orderRepository.findAll();
        final List<SimpleOrderDto> collect = all.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());

        return collect;
    }

    /**
     * fetch join 으로 해결
     *
     * @return
     */
    @GetMapping("/api/v3/simple-order")
    public List<SimpleOrderDto> findOrderV3() {
        final List<Order> allMember = findAllMember();

        final List<SimpleOrderDto> collect = allMember.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());

        return collect;
    }

    private List<Order> findAllMember() {
        return em.createQuery(
                "select o from Order o " +
                        "join fetch o.member m ", Order.class)
                .getResultList();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private OrderStatus orderStatus;
        private LocalDateTime orderDate;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName(); // 지연로딩
            this.orderStatus = order.getOrderStatus();
            this.orderDate = order.getOrderDate();
        }
    }

    /**
     * 선택한 쿼리만 나감
     * 재사용성 X, 매우 불편함
     * @return
     */
    @GetMapping("/api/v4/simple-order")
    public List<OrderSimpleQueryDto> findOrderV4() {
        return em.createQuery(
                "select new com.example.demo.domain.OrderSimpleQueryDto(o.id, m.name, o.orderStatus, o.orderDate)" +
                        "from  Order o " +
                        "join o.member m", OrderSimpleQueryDto.class
        ).getResultList();
    }
}
