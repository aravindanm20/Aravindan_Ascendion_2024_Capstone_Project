package com.example.e_commerce.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private int errorCode;
    private String errorMessage;
}
