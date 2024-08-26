package com.example.e_commerce.controller;



import com.example.e_commerce.dto.*;
import com.example.e_commerce.exception.ProductNotFountException;
import com.example.e_commerce.service.CustomerService;
import com.example.e_commerce.service.OrderService;
import com.example.e_commerce.service.ProductService;
import com.example.e_commerce.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController()
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProduct(@PathVariable("productId") String productId) throws ProductNotFountException {
        if(productService.findByProductId(productId) == null) {
            throw new ProductNotFountException(productId);
        }
        productService.deleteProductById(productId);
    }

    @GetMapping("/findall")
    public List<Product> findAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/findbyname/{productName}")
    public List<Product> findByName(@PathVariable String productName) throws ProductNotFountException {
        if(productService.findByProductName(productName) == null) {
            throw new ProductNotFountException(productName);
        }
        return productService.findByProductName(productName);
    }

    @GetMapping("/findbycategory/{category}")
    public List<Product> findByCategory(@PathVariable String category) {
        return productService.findByCategory(category);
    }

    @GetMapping("/findbyid/{productId}")
    public Product findById(@PathVariable("productId") String productId) throws ProductNotFountException {
        if(productService.findByProductId(productId) == null) {
            throw new ProductNotFountException(productId);
        }
        return productService.findByProductId(productId);
    }
    @PostMapping("/buynow")
    public String buynow(HttpServletRequest request, @RequestParam("productId") String productId) throws ProductNotFountException {
        String token = request.getHeader("Authorization");
        token=token.substring(7);
        String tokenUserName = jwtUtil.getUsername(token);
        User user = customerService.findUserByUsername(tokenUserName);
        Customer customer = customerService.findCustomerByUser(user);
        Product product = productService.findByProductId(productId);
        if(product == null) {
            throw new ProductNotFountException(productId);
        }
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date(System.currentTimeMillis()));
        order.setOrderTotal(product.getPrice());
        OrderLine orderLine = new OrderLine();
        orderLine.setProduct(product);
        orderLine.setQuantity(1);
        orderLine.setOrder(order);
        orderLine.setPrice(product.getPrice());
        order.setOrderLines(List.of(orderLine));
        orderService.save(order);
        return "order place ";
    }
}
