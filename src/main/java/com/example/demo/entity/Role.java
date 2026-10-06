package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    @ManyToMany
    @JoinTable(name = "user_role_mapping", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "priviledge_id"))
    private Set<Privilege> privileges;
}
