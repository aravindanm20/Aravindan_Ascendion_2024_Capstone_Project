package com.example.e_commerce.service;


import com.example.e_commerce.dto.Cart;
import com.example.e_commerce.dto.CartLine;
import com.example.e_commerce.dto.Product;
import com.example.e_commerce.repository.CartLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartLineServiceImpl implements CartLineService {
    @Autowired
    private CartLineRepository cartLineRepository;

    @Override
    public void addCartLine(CartLine cartLine) {
        cartLineRepository.save(cartLine);
    }

    @Override
    public Optional<CartLine> findByCartAndProduct(Cart cart, Product product) {
        return cartLineRepository.findByCartAndProduct(cart, product);
    }

    @Override
    public void removeCartLine(CartLine cartLine) {
        cartLineRepository.delete(cartLine);
    }

    @Override
    public void removeByCart(Cart cart) {
        cartLineRepository.deleteByCart(cart);
    }

    @Override
    public List<CartLine> getByCart(Cart cart) {
        return cartLineRepository.findByCart(cart);
    }

    @Override
    public Double sumByPrice(Cart cart) {
        return cartLineRepository.sumByPrice(cart);
    }
}
