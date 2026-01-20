package com.sakib.quiz_4.services;

import com.sakib.quiz_4.models.Order;
import com.sakib.quiz_4.repositories.OrderRepository;
import com.sakib.quiz_4.serviceInterface.OrderServiceInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService implements OrderServiceInterface {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public boolean addNewOder(Order order) {

        if(order != null){
            orderRepository.save(order);
            return true;
        }
         return false;
    }

    @Override
    public boolean updateOrder(Order order) {

        if(orderRepository.existsById(order.getId())){
            return addNewOder(order);
        }

        return false;

    }

    @Override
    public boolean deleteOrder(String orderId) {

        if(orderRepository.existsById(orderId)){
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }

    @Override
    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public double getTotalRevenue() {
        List<Order> orderList = getAllOrder();
        return orderList.stream().mapToDouble(Order::getProductPrice).sum();
    }

    @Override
    public Integer getTodayOrder() {
        List<Order> orderList = getAllOrder();
        return (int) orderList.stream().filter(o-> o.getPurchaseDate().equals(LocalDate.now())).count();
    }

    @Override
    public Integer getPendingOrderToday() {
        List<Order> orderList = getAllOrder();
        return (int) orderList.stream().filter(o-> o.getPurchaseDate()!=null && o.getPurchaseDate().equals(LocalDate.now()) && o.getOrderStatus().equals("Pending")).count();
    }

    @Override
    public List<Order> getOrderByCustomerName(String customerName) {
        return orderRepository.findByCustomerName(customerName);
    }

    @Override
    public List<Order> getOrderByProductName(String productName) {
        return orderRepository.findByProductName(productName);
    }
}
