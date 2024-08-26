package com.example.e_commerce.repository;


import com.example.e_commerce.dto.Customer;
import com.example.e_commerce.dto.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByCustomer(Customer customer);
}
