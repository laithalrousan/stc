package com.stc.stc.repository;

import com.stc.stc.entities.Permission;
import com.stc.stc.entities.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByPermissionGroupAndUserEmailAndLevel(PermissionGroup permissionGroup, String email, int level);
}
