package com.example.demo.repository;

import com.example.demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @since 2020-07-29
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
