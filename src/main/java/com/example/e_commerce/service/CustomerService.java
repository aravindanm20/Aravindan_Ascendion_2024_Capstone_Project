package com.example.e_commerce.service;

import com.example.e_commerce.dto.Customer;
import com.example.e_commerce.dto.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    Customer findCustomerByUser(User user);
    Customer findCustomerById(String customerId);
    List<Customer> findAllCustomers();
    Customer updateCustomer(Customer customer);
    User findUserByUsername(String username);
}
