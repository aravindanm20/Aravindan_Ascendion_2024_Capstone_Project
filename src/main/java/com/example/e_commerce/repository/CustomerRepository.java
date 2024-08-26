package com.example.e_commerce.repository;

import com.example.e_commerce.dto.Customer;
import com.example.e_commerce.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findUserByUsername(@Param("username") String username);
    @Query("SELECT c FROM Customer c WHERE c.user = :user")
    Customer findCustomerByUser(@Param("user") User user);
    Customer findByCustomerId(String customerId);
}
