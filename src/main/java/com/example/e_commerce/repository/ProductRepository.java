package com.example.e_commerce.repository;



import com.example.e_commerce.dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    public List<Product> findByCategory(String category);
    public Product findByProductId(String productId);
    public List<Product> findByProductName(String productName);
}
