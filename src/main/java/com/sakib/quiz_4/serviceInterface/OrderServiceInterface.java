package com.sakib.quiz_4.serviceInterface;

import com.sakib.quiz_4.models.Order;

import java.util.List;

public interface OrderServiceInterface {

    boolean addNewOder(Order order);
    boolean updateOrder(Order order);
    boolean deleteOrder(String orderId);
    Order getOrder(String orderId);
    List<Order> getAllOrder();
    double getTotalRevenue();
    Integer getTodayOrder();
    Integer getPendingOrderToday();
    List<Order> getOrderByCustomerName(String customerName);
    List<Order> getOrderByProductName(String productName);

}
