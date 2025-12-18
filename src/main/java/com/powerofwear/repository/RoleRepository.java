package com.powerofwear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.powerofwear.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(String name);
}
