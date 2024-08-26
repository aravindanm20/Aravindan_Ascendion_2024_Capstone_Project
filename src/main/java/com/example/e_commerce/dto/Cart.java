package com.example.e_commerce.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {
    @Id
    private String cartId;

    @OneToOne(fetch = FetchType.EAGER)
    private Customer customer;


    @PrePersist
    public void generateId() {
        cartId=customer.getCustomerId().toUpperCase()
                +customer.getCustomerId().toUpperCase().hashCode();
    }
}
