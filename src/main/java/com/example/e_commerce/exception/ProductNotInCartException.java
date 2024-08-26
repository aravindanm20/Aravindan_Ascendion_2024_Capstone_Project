package com.example.e_commerce.exception;

public class ProductNotInCartException extends Exception {
    String productId;

    public ProductNotInCartException(String productId) {
        this.productId = productId;
    }
    public String getMessage() {
        return productId+" is not in your cart.";
    }
}
