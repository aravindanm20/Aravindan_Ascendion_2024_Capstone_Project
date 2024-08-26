package com.example.e_commerce.service;




import com.example.e_commerce.dto.Product;
import com.example.e_commerce.exception.ProductNotFountException;

import java.util.List;

public interface ProductService {
    public Product save(Product product);
    public Product update(Product product);
    public void deleteProductById(String productId) throws ProductNotFountException;
    public List<Product> getAllProducts();
    public List<Product> findByCategory(String category);
    public Product findByProductId(String productId)throws ProductNotFountException;
    public List<Product> findByProductName(String productName) throws ProductNotFountException;


}
