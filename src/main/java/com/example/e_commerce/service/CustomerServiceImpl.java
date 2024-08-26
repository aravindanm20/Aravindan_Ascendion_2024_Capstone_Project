package com.example.e_commerce.service;

import com.example.e_commerce.dto.Customer;
import com.example.e_commerce.dto.User;
import com.example.e_commerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public Customer save(Customer customer) {
        User user = customer.getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    @Override
    public Customer findCustomerByUser(User user) {
        return customerRepository.findCustomerByUser(user);
    }

    @Override
    public Customer findCustomerById(String customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public User findUserByUsername(String username) {
        return customerRepository.findUserByUsername(username);
    }
}
