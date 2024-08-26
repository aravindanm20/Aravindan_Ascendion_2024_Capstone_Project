package com.example.e_commerce.exception;

public class UsernameAlreadyExistException extends Exception {
    String username;

    public UsernameAlreadyExistException(String username) {
        this.username = username;
    }
    public String getMessage() {
        return  username + " username already exists";
    }
}
