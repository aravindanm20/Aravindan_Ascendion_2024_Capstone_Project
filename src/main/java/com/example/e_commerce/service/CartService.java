package com.example.e_commerce.service;



import com.example.e_commerce.dto.Cart;
import com.example.e_commerce.dto.Customer;
import com.example.e_commerce.dto.Product;
import com.example.e_commerce.dto.User;
import com.example.e_commerce.exception.CustomerNotFoundException;
import com.example.e_commerce.exception.ProductNotFountException;
import org.springframework.stereotype.Repository;

@Repository
public interface CartService {
    public Customer findCustomerByUser(User user);
    User findUserByUsername(String username);
    public Product findProductById(String productId) throws ProductNotFountException;
    public void addToCart(Cart cart);
    public Cart findCartByCustomer(Customer customer);
    public void removeFromCart(Cart cart);

}
