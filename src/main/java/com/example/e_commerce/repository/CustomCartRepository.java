package com.example.e_commerce.repository;


import com.example.e_commerce.dto.Customer;
import com.example.e_commerce.dto.Product;
import com.example.e_commerce.dto.User;
import com.example.e_commerce.exception.CustomerNotFoundException;
import com.example.e_commerce.exception.ProductNotFountException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCartRepository {
    @Query("SELECT c FROM Customer c WHERE c.user = :user")
    Customer findCustomerByUser(@Param("user") User user);
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findUserByUsername(@Param("username") String username);
    @Query("SELECT p FROM Product p WHERE p.productId = :productId")
    Product findByProductId(@Param("productId") String productId);
    //Product findByProductId(String productId) throws ProductNotFountException;
}
