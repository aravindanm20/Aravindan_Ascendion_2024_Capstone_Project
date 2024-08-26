package com.example.e_commerce.service;

import com.example.e_commerce.dto.User;

public interface UserService {
    public User save(User user);
    public User findByUsername(String username);
}
