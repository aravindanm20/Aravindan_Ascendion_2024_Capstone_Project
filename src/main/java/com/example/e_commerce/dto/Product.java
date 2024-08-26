package com.example.e_commerce.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String productId;
    private String productName;
    private String productModel;
    private String category;
    private String productColor;
    private float price;
    private int quantity;


    @PrePersist
    public void generateId(){
        productId=category.substring(0,2).toUpperCase()
                + productName.substring(0,2).toUpperCase()
                + productModel.substring(0,2).toUpperCase()
                + productColor.substring(0,2).toUpperCase()
                + UUID.randomUUID().toString().substring(0,4).toUpperCase();

    }
}
