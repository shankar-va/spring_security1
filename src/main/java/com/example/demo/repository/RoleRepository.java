package com.example.demo.repository;

import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("""
        SELECT DISTINCT r
        FROM Role r
        LEFT JOIN FETCH r.privileges
        WHERE r IN :roles
    """)
    List<Role> findRolesWithPrivileges(
            @Param("roles") Set<Role> roles
    );
}