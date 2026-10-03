package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class Public {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @GetMapping("/")
    public String homepage(){
        return "Welcome to spring application";
    }
    @PostMapping("/auth/register")
    public User register(@RequestBody User  user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user)).orElseThrow(()->new UsernameNotFoundException("Cannot register user!!!..."));
    }
}
