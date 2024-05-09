package com.tacm.tabooksapi.service;

import com.tacm.tabooksapi.domain.dto.CartItemsRq;
import com.tacm.tabooksapi.domain.entities.Orders;
import com.tacm.tabooksapi.domain.entities.Users;
import com.tacm.tabooksapi.exception.OrderException;
import com.tacm.tabooksapi.exception.ProductException;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    public Orders createOrder(Users users, CartItemsRq cartItemsRq) throws ProductException;

    public Orders findOderById(Long id) throws OrderException;

    public List<Orders> userOrderHistory(Long userId);

    public Orders placedOrder(Long id) throws  OrderException;

    public Orders confirmedOrder(Long id) throws  OrderException;

    public Orders shippedOrder(Long id) throws  OrderException;
    public Orders deliveredOrder(Long id) throws  OrderException;
    public void canceledOrder(Long id) throws  OrderException;

    List<Orders> getAllOrders();

    void deletedOrder(Long orderId) throws OrderException;

    List<Orders> filterPendingOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime);

    List<Orders> filterConfirmedOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime);

    List<Orders> filterShippingOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime);

    List<Orders> filterDeliveredOrder(String keyword, LocalDateTime startTime, LocalDateTime endTime);
}
