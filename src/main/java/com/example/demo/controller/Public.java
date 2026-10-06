package com.example.demo.controller;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class Public {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    @GetMapping("/")
    public String homepage(){
        return "Welcome to spring application";
    }
    @PostMapping("/auth/register")
    public User register(@RequestBody UserRequestDTO user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(userMapper.toEntity(user))).orElseThrow(()->new UsernameNotFoundException("Cannot register user!!!..."));
    }
    @PostMapping("auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(),request.password()));
        UserDetails user=userDetailsService.loadUserByUsername(request.email());
        String jwtToken=jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }
    @GetMapping("/test-auth")
    public String testAuth(Authentication authentication) {
        return "Authenticated as: " + authentication.getName();
    }
}
