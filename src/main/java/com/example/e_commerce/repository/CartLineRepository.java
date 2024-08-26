package com.example.e_commerce.repository;



import com.example.e_commerce.dto.Cart;
import com.example.e_commerce.dto.CartLine;
import com.example.e_commerce.dto.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartLineRepository extends JpaRepository<CartLine, String> {
    public List<CartLine> findByCart(Cart cart);
    public Optional<CartLine> findByCartAndProduct(Cart cart, Product product);
    @Transactional
    public void deleteByCart(Cart cart);
    @Query("SELECT SUM(cl.price) FROM CartLine cl WHERE cl.cart = :cart")
    public Double sumByPrice(@Param("cart")Cart cart);
}
