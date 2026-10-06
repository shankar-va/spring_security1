package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User_info")
public class User {
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @ManyToMany
    @JoinTable(name = "role_info", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled=true;
    @Column(nullable = false)
    @Builder.Default
    private Boolean accountNonLocked=true;
    @Column(nullable = false)
    @Builder.Default
    private Boolean accountNonExpired=true;
    @Column(nullable = false)
    @Builder.Default
    private Boolean credentialsNonExpired=true;
}
