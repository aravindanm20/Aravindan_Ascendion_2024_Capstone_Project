package com.example.e_commerce.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin {
    @Id
    private String adminId;
    private String adminName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;

    @PrePersist
    public void generateId(){
        this.adminId=this.adminName.substring(0,4).toUpperCase()
                     + UUID.randomUUID().toString().substring(0,4).toUpperCase();
    }
}
