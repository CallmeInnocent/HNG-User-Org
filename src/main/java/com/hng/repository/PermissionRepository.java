package com.hng.repository;

import com.hng.entity.Permission;
import com.hng.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, String> {


}
