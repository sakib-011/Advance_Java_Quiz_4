package com.sakib.quiz_4.repositories;

import com.sakib.quiz_4.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByCustomerName(String customerName);
    List<Order> findByProductName(String productName);

}
