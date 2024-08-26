package com.example.e_commerce.repository;



import com.example.e_commerce.dto.Cart;
import com.example.e_commerce.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String>,CustomCartRepository {
    public Cart findByCustomer(Customer customer);

}
