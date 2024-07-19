package com.hng.service;


import com.hng.dto.OrganisationData;
import com.hng.dto.OrganisationDetail;
import com.hng.dto.OrganisationResponseDTO;
import com.hng.entity.Role;
import com.hng.entity.Organisation;
import com.hng.entity.Permission;
import com.hng.entity.User;
import com.hng.repository.OrganisationRepository;
import com.hng.repository.RoleRepository;
import com.hng.repository.PermissionRepository;
import com.hng.repository.UserRepository; // Add UserRepository for fetching users
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private UserRepository userRepository; // Add UserRepository for fetching users

    public void createRole(String roleName, String orgId, List<String> permissionNames, Authentication authentication) {

        User currentUser = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));


        Role role = new Role();

        log.info("USER_INFO: {}", currentUser.toString());

        role.getUsers().add(currentUser);

        role.setRoleName(roleName);

        assignOrganisationToRole(orgId, role);

        assignPermissionsToRole(permissionNames, role);

        roleRepository.save(role);
    }


    // Assign permissions to a role
    private void assignOrganisationToRole(String orgId, Role role) {

        // Fetch organisation and set it to role
        Organisation organisation = organisationRepository.findById(orgId).orElseThrow();
        role.setOrganisation(organisation);
    }

    // Assign permissions to a role
    private void assignPermissionsToRole(List<String> permissionNames, Role role) {
        // Fetch permissions based on the list of IDs
        List<Permission> permissions = new ArrayList<>();
        for (String permissionName : permissionNames) {
            Permission permission = new Permission();
            permission.setName(permissionName);
            permissions.add(permission);
        }
        role.setPermissions(permissions);
    }

    // Assign a role to a user
    public void assignRoleToUser(String userId, String roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    // Assign  roles to a user
    public void assignRolesToUser(String userId, List<String> roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Role> roles = roleRepository.findAllById(roleId);
        for (Role role : roles) {
            user.getRoles().add(role);
        }
        userRepository.save(user);
    }
}

