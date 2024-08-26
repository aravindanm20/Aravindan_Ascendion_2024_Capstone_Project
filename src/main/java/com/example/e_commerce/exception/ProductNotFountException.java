package com.example.e_commerce.exception;

public class ProductNotFountException extends Exception {
    private String product;
    public ProductNotFountException(String product) {
        this.product = product;
    }
    public String getMessage() {
        return "Product " +product+" is not fount";
    }
}
