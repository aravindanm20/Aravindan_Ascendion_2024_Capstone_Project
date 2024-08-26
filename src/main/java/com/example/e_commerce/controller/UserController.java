package com.example.e_commerce.controller;

import com.example.e_commerce.dto.*;
import com.example.e_commerce.exception.UsernameAlreadyExistException;
import com.example.e_commerce.service.AdminService;
import com.example.e_commerce.service.CustomerService;
import com.example.e_commerce.service.UserService;
import com.example.e_commerce.util.JwtUtil;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AdminService adminService;

    @Autowired
    CustomerService customerService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/admin/signup")
    public ResponseEntity<Admin> signup(@RequestBody Admin admin) throws UsernameAlreadyExistException {
        if(userService.findByUsername(admin.getUser().getUsername())==null)
        {
            Admin newAdmin = adminService.save(admin);
            return ResponseEntity.ok(newAdmin);
        }
        else {
            throw new UsernameAlreadyExistException(admin.getUser().getUsername());
        }
    }

    @PostMapping("/customer/signup")
    public ResponseEntity<Customer> signup(@RequestBody Customer customer) throws UsernameAlreadyExistException {
        if(userService.findByUsername(customer.getUser().getUsername())==null){
            Customer newCustomer = customerService.save(customer);
            return ResponseEntity.ok(newCustomer);
        }
        else {
            throw new UsernameAlreadyExistException(customer.getUser().getUsername());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginUserRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUserRequest.getUsername(), loginUserRequest.getPassword()
        ));
        String token=jwtUtil.generateToken(loginUserRequest.getUsername());
        return ResponseEntity.ok(new LoginResponse(token,"Token Created",userService.findByUsername(loginUserRequest.getUsername())));
    }

}
