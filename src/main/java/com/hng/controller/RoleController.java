package com.hng.controller;


import com.hng.dto.AssignRoleToUserResponseDto;
import com.hng.dto.RoleCreationRequestDto;
import com.hng.dto.RoleCreationResponseDto;
import com.hng.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<RoleCreationResponseDto> createRole(@RequestBody RoleCreationRequestDto createRequestDto, Authentication authentication) {
        try {
            roleService.createRole(createRequestDto.getRoleName(), createRequestDto.getOrganisationId(), createRequestDto.getPermissionNames(), authentication);
            RoleCreationResponseDto responseDto = new RoleCreationResponseDto("201", "Role created successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            RoleCreationResponseDto responseDto = new RoleCreationResponseDto(" 400", "Role creation failed.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }

    }

    // Endpoint to assign a role to a user
    @PostMapping("/{roleId}/assign-to-user/{userId}")
    public ResponseEntity<AssignRoleToUserResponseDto> assignRoleToUser(@PathVariable String roleId, @PathVariable String userId, Authentication authentication) {
        try {
            roleService.assignRoleToUser(userId, roleId,authentication);

            AssignRoleToUserResponseDto responseDto = new AssignRoleToUserResponseDto("200", "Role assigned to user successfully");
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            AssignRoleToUserResponseDto responseDto = new  AssignRoleToUserResponseDto ("400", "Error assigning role to user:" + e.getMessage());
            return ResponseEntity.status(400).body(responseDto);
        }
    }
//
//    // Endpoint to assign multiple roles to a user
//    @PostMapping("/{userId}/assign-roles")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPER_ADMIN')")
//    public ResponseEntity<ResponseDto> assignRolesToUser(@PathVariable String userId, @RequestBody List<String> roleIds, Authentication authentication) {
//        try {
//            roleService.assignRolesToUser(userId, roleIds,authentication);
//
//            ResponseDto responseDto = new ResponseDto("Roles assigned to user successfully", null);
//            return ResponseEntity.ok(responseDto);
//        } catch (Exception e) {
//            ResponseDto responseDto = new ResponseDto("Error assigning roles to user: " + e.getMessage(), null);
//            return ResponseEntity.status(500).body(responseDto);
//        }
//    }
}
