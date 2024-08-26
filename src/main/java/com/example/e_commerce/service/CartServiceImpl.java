package com.example.e_commerce.service;



import com.example.e_commerce.dto.Cart;
import com.example.e_commerce.dto.Customer;
import com.example.e_commerce.dto.Product;
import com.example.e_commerce.dto.User;
import com.example.e_commerce.exception.CustomerNotFoundException;
import com.example.e_commerce.exception.ProductNotFountException;
import com.example.e_commerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;


    @Override
    public Customer findCustomerByUser(User user){
        return cartRepository.findCustomerByUser(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return cartRepository.findUserByUsername(username);
    }

    @Override
    public Product findProductById(String productId) throws ProductNotFountException {
        return cartRepository.findByProductId(productId);
    }

    @Override
    public void addToCart(Cart cart) {
        cartRepository.save(cart);

    }

    @Override
    public Cart findCartByCustomer(Customer customer) {
        return cartRepository.findByCustomer(customer);
    }

    @Override
    public void removeFromCart(Cart cart) {
        cartRepository.delete(cart);
    }
}
