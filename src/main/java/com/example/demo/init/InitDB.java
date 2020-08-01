package com.example.demo.init;

import com.example.demo.domain.Book;
import com.example.demo.domain.Member;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * @since 2020-07-29
 */
@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.doService1();
        initService.doService2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void doService1() {
            Member member = new Member();
            member.setName("andrew");
            em.persist(member);

            Book book = new Book();
            book.setName("Effective Java");
            book.setPrice(10000);
            em.persist(book);

            Book book1 = new Book();
            book1.setName("Head first design pattern");
            book1.setPrice(10000);
            em.persist(book1);

            OrderItem orderItem = OrderItem.createOrderItem(book, 20000, 2);
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 30000, 3);

            Order order = Order.createOrder(member, orderItem, orderItem1);
            em.persist(order);

        }

        public void doService2() {
            Member member = new Member();
            member.setName("Ted");
            em.persist(member);

            Book book = new Book();
            book.setName("Spring in action");
            book.setPrice(15000);
            em.persist(book);

            Book book1 = new Book();
            book1.setName("Toby Spring");
            book1.setPrice(15000);
            em.persist(book1);

            OrderItem orderItem = OrderItem.createOrderItem(book, 30000, 2);
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 30000, 2);

            Order order = Order.createOrder(member, orderItem, orderItem1);
            em.persist(order);
        }


    }
}
