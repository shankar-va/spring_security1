package com.example.demo.security;

import com.example.demo.entity.User;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorites= user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toSet());
        user.getRoles().stream().flatMap(role->role.getPrivileges().stream()).map(privilege -> new SimpleGrantedAuthority(privilege.getPrivilege())).forEach(privilege->authorites.add(privilege));
        return authorites;
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
