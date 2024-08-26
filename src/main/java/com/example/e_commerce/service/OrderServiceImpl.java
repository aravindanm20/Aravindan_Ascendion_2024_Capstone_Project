package com.example.e_commerce.service;


import com.example.e_commerce.dto.Customer;
import com.example.e_commerce.dto.Order;
import com.example.e_commerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> findByCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
