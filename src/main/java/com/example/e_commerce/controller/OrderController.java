package com.example.e_commerce.controller;


import com.example.e_commerce.dto.Order;
import com.example.e_commerce.service.OrderLineService;
import com.example.e_commerce.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkout")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("/placeorder")
    public String placeOrder(@RequestBody Order order) {
        orderService.save(order);
        return "Order placed , Order Id : " + order.getOrderId() ;
    }

    @GetMapping("/viewallorders")
    public List<Order> viewAllOrders() {
        return orderService.findAll();
    }


}
