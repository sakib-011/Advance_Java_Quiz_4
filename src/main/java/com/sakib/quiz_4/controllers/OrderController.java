package com.sakib.quiz_4.controllers;

import com.sakib.quiz_4.models.Order;
import com.sakib.quiz_4.services.OrderService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Cacheable(cacheNames = "orders" , key = "'all'")
    @GetMapping("/api/get-orders")
    public List<Order> getAllOrder(){
        System.out.println("In Get all order method");
        return orderService.getAllOrder();
    }

    @Cacheable(cacheNames = "orderById" , key = "#orderId")
    @GetMapping("/api/get-order-id{orderId}")
    public Order getOrderById(@PathVariable String orderId){
        return orderService.getOrder(orderId);
    }

    @Cacheable(cacheNames = "orderByCustomerName" , key = "#customer_name")
    @GetMapping("/api/get-order-customer-name/{customer_name}")
    public List<Order> getOrderByCustomerName(@PathVariable String customer_name){
        return orderService.getOrderByCustomerName(customer_name);
    }

    @Cacheable(cacheNames = "orderByProductName" , key = "#product_name")
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
    @Caching(evict = {
            @CacheEvict(cacheNames = "orders" , allEntries = true),
    })
    @PostMapping("/api/add-new-order")
    public Boolean addNewOrder(@RequestBody Order order){

        System.out.println("=== Order Received ===");
        System.out.println("Product Name: " + order.getProductName());
        System.out.println("Customer Name: " + order.getCustomerName());
        System.out.println("Quantity: " + order.getProductQuantity());
        System.out.println("Price: " + order.getProductPrice());
        System.out.println("Order Status: " + order.getOrderStatus());
        System.out.println("Order Date: " + order.getPurchaseDate());
        System.out.println("Full Order Object: " + order);

        return orderService.addNewOder(order);
    }


    @Caching(evict = {
            @CacheEvict(cacheNames = "orders" , allEntries = true),
            @CacheEvict(cacheNames = "orderByProductName" , allEntries = true),
            @CacheEvict(cacheNames = "orderByCustomerName" , allEntries = true),
            @CacheEvict(cacheNames = "orderById" ,  key = "#order.id")
    })
    @PutMapping("/api/update/order")
    public Boolean updateOrder(@RequestBody Order order){

        System.out.println("Update Order : ");
        System.out.println("Order Product Name : " + order.getProductName());
        System.out.println("Customer Name : " + order.getCustomerName());

        order.setPurchaseDate(orderService.getOrder(order.getId()).getPurchaseDate());

        return orderService.updateOrder(order);
    }


    @Caching(evict = {
            @CacheEvict(cacheNames = "orders" , allEntries = true),
            @CacheEvict(cacheNames = "orderByProductName" , allEntries = true),
            @CacheEvict(cacheNames = "orderByCustomerName" , allEntries = true),
            @CacheEvict(cacheNames = "orderById" , key = "#orderId")
    })
    @DeleteMapping("/api/delete/order/{orderId}")
    public Boolean deleteOrder(@PathVariable String orderId){
        return orderService.deleteOrder(orderId);
    }


}
