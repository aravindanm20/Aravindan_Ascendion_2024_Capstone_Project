package com.example.e_commerce.exception;

public class CartEmptyException extends Exception{
    private String customerId;

    public CartEmptyException(String customerId) {
        this.customerId = customerId;
    }
    public String getMessage() {
        return customerId+" Cart is empty";
    }
}
