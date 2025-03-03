package com.deepon.service.Order;

import com.deepon.model.*;
import com.deepon.repository.*;
import com.deepon.request.OrderRequest;
import com.deepon.service.Cart.CartService;
import com.deepon.service.Restaurant.RestaurantService;
import com.deepon.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;



    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        Address shippingAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shippingAddress);
        if(!user.getAddress().contains(savedAddress)){
            user.getAddress().add(savedAddress);
            userRepository.save(user);
        }
        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());
        Order createdOrder = new Order();

        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart = cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem: cart.getItem()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem orderItemSaved = orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }
        Long totalPrice = cartService.calculateCartTotal(cart);

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);
        Order orderSaved = orderRepository.save(createdOrder);
        restaurant.getOrders().add(orderSaved);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")
        ){
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("PLease select a valid order");
    }

    @Override
    public void cancleOrder(Long orderId) throws Exception {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders= orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus != null){
            orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw new Exception("Order not found");
        }
        return optionalOrder.get();
    }
}
