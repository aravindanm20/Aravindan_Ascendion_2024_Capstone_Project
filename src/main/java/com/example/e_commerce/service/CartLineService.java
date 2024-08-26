package com.example.e_commerce.service;



import com.example.e_commerce.dto.Cart;
import com.example.e_commerce.dto.CartLine;
import com.example.e_commerce.dto.Product;

import java.util.List;
import java.util.Optional;

public interface CartLineService {
    public void addCartLine(CartLine cartLine);
    public Optional<CartLine> findByCartAndProduct(Cart cart, Product product);
    public void  removeCartLine(CartLine cartLine);
    public void removeByCart(Cart cart);
    public List<CartLine> getByCart(Cart cart);
    public Double sumByPrice(Cart cart);
}
