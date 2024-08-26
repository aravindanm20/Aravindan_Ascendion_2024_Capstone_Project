package com.example.e_commerce.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    private String customerId;
    private String customerName;
    private Date dateOfBirth;
    private String city;
    private int pinCode;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;

    @PrePersist
    public void generateId() {
        this.customerId=this.customerName.substring(0,3).toUpperCase()
                +this.dateOfBirth.toLocalDate().getDayOfMonth()
                + UUID.randomUUID().toString().substring(0,4).toUpperCase();
    }
}
