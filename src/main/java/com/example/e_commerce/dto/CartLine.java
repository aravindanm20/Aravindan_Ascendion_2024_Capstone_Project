package com.example.e_commerce.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartLine {
    @Id
    private String cartLineId;
    private int quantity;
    private double price;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @PrePersist
    public  void generateId() {
        cartLineId= cart.getCartId().substring(0,3).toUpperCase()
                + product.getProductId().substring(0,3).toUpperCase()
                +(cart.getCartId()+product.getProductId()).hashCode();
    }
}
