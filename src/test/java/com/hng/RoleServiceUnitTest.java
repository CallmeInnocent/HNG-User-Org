package com.hng;


import com.hng.entity.Organisation;
import com.hng.entity.Role;
import com.hng.entity.User;
import com.hng.repository.OrganisationRepository;
import com.hng.repository.PermissionRepository;
import com.hng.repository.RoleRepository;
import com.hng.repository.UserRepository;
import com.hng.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RoleServiceUnitTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private OrganisationRepository organisationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRole_Success() {
        String roleName = "ROLE_TEST";
        String orgId = "ORG123";
        List<String> permissionNames = List.of("READ_PRIVILEGES", "WRITE_PRIVILEGES");

        Organisation organisation = new Organisation();
        when(organisationRepository.findById(orgId)).thenReturn(Optional.of(organisation));

        roleService.createRole(roleName, orgId, permissionNames);

        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void testCreateRole_OrganisationNotFound() {
        String roleName = "ROLE_TEST";
        String orgId = "ORG123";
        List<String> permissionNames = List.of("READ_PRIVILEGES", "WRITE_PRIVILEGES");

        when(organisationRepository.findById(orgId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roleService.createRole(roleName, orgId, permissionNames));
    }

    @Test
    void testAssignRoleToUser_Success() {
        String userId = "USER123";
        String roleId = "ROLE123";

        User user = new User();
        Role role = new Role();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        roleService.assignRoleToUser(userId, roleId);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testAssignRoleToUser_UserNotFound() {
        String userId = "USER123";
        String roleId = "ROLE123";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roleService.assignRoleToUser(userId, roleId));
    }

    @Test
    void testAssignRoleToUser_RoleNotFound() {
        String userId = "USER123";
        String roleId = "ROLE123";

        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roleService.assignRoleToUser(userId, roleId));
    }
}

