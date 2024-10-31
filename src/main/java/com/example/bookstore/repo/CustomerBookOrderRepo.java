package com.example.bookstore.repo;

import com.example.bookstore.entities.CustomerBookOrder;
import com.example.bookstore.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerBookOrderRepo extends JpaRepository<CustomerBookOrder,Long> {
    List<CustomerBookOrder> findCustomerBookOrderByOrderStatus(OrderStatus orderStatus);
}
