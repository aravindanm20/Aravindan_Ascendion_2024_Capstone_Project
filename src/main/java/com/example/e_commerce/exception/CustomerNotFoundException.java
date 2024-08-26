package com.example.e_commerce.exception;

public class CustomerNotFoundException extends Exception{
    private String customerID;

    public CustomerNotFoundException(String customerID) {
        this.customerID = customerID;
    }

    public CustomerNotFoundException() {
        super();
    }
    public String getMessage(){
        return "Customer " +customerID+" not found";
    }
}
