package com.example.e_commerce.service;

import com.example.e_commerce.dto.Admin;
import com.example.e_commerce.dto.User;
import com.example.e_commerce.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin save(Admin admin) {
        User user = admin.getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        admin.setUser(user);
        return adminRepository.save(admin);
    }
}
