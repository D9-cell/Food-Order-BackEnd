package com.deepon.repository;

import com.deepon.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByCustomerId(Long userid);
    List<Order> findByRestaurantId(Long restaurantId);
}
