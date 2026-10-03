package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String email) {
        User user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
        return new CustomUserDetails(user);
    }
}
