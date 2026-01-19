package com.sakib.quiz_4.controllers;

import com.sakib.quiz_4.models.Order;
import com.sakib.quiz_4.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//    Get Mapping
    @GetMapping("/api/get-orders")
    public List<Order> getAllOrder(){
        return orderService.getAllOrder();
    }

    @GetMapping("/api/get-order-id/{orderId}")
    public Order getOrderById(@PathVariable String orderId){
        return orderService.getOrder(orderId);
    }

    @GetMapping("/api/get-order-customer-name/{customer_name}")
    public List<Order> getOrderByCustomerName(@PathVariable String customer_name){
        return orderService.getOrderByCustomerName(customer_name);
    }

    @GetMapping("/api/get-order-product-name/{product_name}")
    public List<Order> getOrderByProductName(@PathVariable String product_name){
        return orderService.getOrderByProductName(product_name);
    }

    @GetMapping("/api/get-total-revenu")
    public Double getTotalRevenue(){
        return orderService.getTotalRevenue();
    }

    @GetMapping("/api/get-today-order-count")
    public Integer getTotalOrderToday(){
        return orderService.getTodayOrder();
    }

    @GetMapping("/api/get-pending-order-count")
    public Integer getPendingOderCount(){
        return orderService.getPendingOrderToday();
    }



//    Post Mapping
    @PostMapping("/api/add-new-order")
    public Boolean addNewOrder(@RequestParam Order order){
        return orderService.addNewOder(order);
    }

    @PostMapping("/api/update/order")
    public Boolean updateOrder(@RequestParam Order order){
        return orderService.updateOrder(order);
    }

    @PostMapping("/api/delete/order/{orderId}")
    public Boolean deleteOrder(@PathVariable String orderId){
        return orderService.deleteOrder(orderId);
    }


}
