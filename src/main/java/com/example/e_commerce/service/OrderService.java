package com.example.e_commerce.service;


import com.example.e_commerce.dto.Customer;
import com.example.e_commerce.dto.Order;

import java.util.List;

public interface OrderService {
    public void save(Order order);
    List<Order> findByCustomer(Customer customer);
    List<Order> findAll();
}
