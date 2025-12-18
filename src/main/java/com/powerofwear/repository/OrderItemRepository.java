package com.powerofwear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.powerofwear.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
