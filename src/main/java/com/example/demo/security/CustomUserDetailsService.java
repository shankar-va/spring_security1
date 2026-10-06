package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {


        User user = userRepository.findUserWithRoles(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Invalid email or password"
                        ));

        roleRepository.findRolesWithPrivileges(user.getRoles());

        return new CustomUserDetails(user);
    }
}
