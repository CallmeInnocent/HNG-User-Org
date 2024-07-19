package com.hng.controller;


import com.hng.dto.RoleCreationRequestDto;
import com.hng.dto.RoleCreationResponseDto;
import com.hng.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_ADMIN')")
    public ResponseEntity<RoleCreationResponseDto> createRole(@RequestBody RoleCreationRequestDto createRequestDto) {
        try {
             roleService.createRole(createRequestDto.getRoleName(), createRequestDto.getOrganisationId(), createRequestDto.getPermissionNames());
            RoleCreationResponseDto responseDto = new RoleCreationResponseDto("201", "Role created successfully.");
            return ResponseEntity.status(201).body(responseDto);
        } catch (Exception e) {
            RoleCreationResponseDto responseDto = new RoleCreationResponseDto(" 400", "Role creation failed.");
            return ResponseEntity.status(400).body(responseDto);
        }
    }

//    // Endpoint to assign a role to a user
//    @PostMapping("/{roleId}/assign-to-user/{userId}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_ADMIN')")
//    public ResponseEntity<ResponseDto> assignRoleToUser(@PathVariable String roleId, @PathVariable String userId) {
//        try {
//            roleService.assignRoleToUser(userId, roleId);
//
//            ResponseDto responseDto = new ResponseDto("Role assigned to user successfully", null);
//            return ResponseEntity.ok(responseDto);
//        } catch (Exception e) {
//            ResponseDto responseDto = new ResponseDto("Error assigning role to user: " + e.getMessage(), null);
//            return ResponseEntity.status(500).body(responseDto);
//        }
//    }
//
//    // Endpoint to assign multiple roles to a user
//    @PostMapping("/{userId}/assign-roles")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_ADMIN')")
//    public ResponseEntity<ResponseDto> assignRolesToUser(@PathVariable String userId, @RequestBody List<String> roleIds) {
//        try {
//            roleService.assignRolesToUser(userId, roleIds);
//
//            ResponseDto responseDto = new ResponseDto("Roles assigned to user successfully", null);
//            return ResponseEntity.ok(responseDto);
//        } catch (Exception e) {
//            ResponseDto responseDto = new ResponseDto("Error assigning roles to user: " + e.getMessage(), null);
//            return ResponseEntity.status(500).body(responseDto);
//        }
//    }
}
