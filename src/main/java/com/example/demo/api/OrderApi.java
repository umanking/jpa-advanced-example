package com.example.demo.api;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.OrderStatus;
import com.example.demo.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private final EntityManager em;


    @GetMapping("/api/v1/orders")
    public List<Order> findOrdersV1() {
        List<Order> all = orderRepository.findAll();
        for (Order order : all) {
            order.getMember().getName();
            order.getOrderItems().forEach(o -> o.getBook().getName());
        }

        return all;
    }

    // 쿼리 order 1 member 2, orderItem 2, book 4
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> all = orderRepository.findAll();
        List<OrderDto> collect = all.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return collect;
    }

    // todo: collection fetch join 으로 성능 최적화 
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> all = findAllWithItems();
        List<OrderDto> collect = all.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return collect;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_paging(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                          @RequestParam(value = "limit", defaultValue = "100") int limit) {
        // toOne은 fetch 조인으로
        List<Order> all = findAllWithMemberPaging(offset, limit);

        // orderItem toMany 는 spring.jpa.properties.hibernate.default_batch_fetch_size=100  옵션을 주면 -> in Query로 미리 만들어줌
        List<OrderDto> collect = all.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return collect;
    }

    private List<Order> findAllWithItems() {
        return em.createQuery(
                "select distinct o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.orderItems oi" +
                        " join fetch oi.book b"
                , Order.class).getResultList();
    }

    @Data
    static class OrderDto {

        private Long orderId;
        private String name;
        private OrderStatus orderStatus;
        private LocalDateTime orderDate;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderStatus = order.getOrderStatus();
            orderDate = order.getOrderDate();
            orderItems = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Data
    static class OrderItemDto {

        private String itemName;
        private int orderPirce;

        public OrderItemDto(OrderItem orderItem) {
            this.itemName = orderItem.getBook().getName();
            this.orderPirce = orderItem.getOrderPirce();
        }
    }

    private List<Order> findAllWithMember() {
        return em.createQuery(
                "select o from Order o " +
                        "join fetch o.member m ", Order.class)
                .getResultList();
    }


    private List<Order> findAllWithMemberPaging(int offset, int limit) {
        return em.createQuery(
                "select o from Order o " +
                        "join fetch o.member m ", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList()


                ;
    }
}
