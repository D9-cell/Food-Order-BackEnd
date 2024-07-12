package com.deepon.service.Order;

import com.deepon.model.Order;
import com.deepon.model.User;
import com.deepon.request.OrderRequest;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest order, User user) throws Exception;
    Order updateOrder(Long orderId,String orderStatus) throws Exception;
    void cancleOrder(Long orderId) throws Exception;
    List<Order> getUsersOrder(Long userId) throws Exception;
    List<Order> getRestaurantOrder(Long restaurantId,String orderStatus) throws Exception;
    Order findOrderById(Long orderId) throws Exception;
}
