package com.hng.controller;

import com.hng.dto.*;
import com.hng.exception.UserNotFoundException;
import com.hng.service.OrganisationService;
import com.hng.service.UserService;
import com.hng.entity.Organisation;
import com.hng.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/organisations")
public class OrganisationController {

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<OrganisationResponseDTO> getUserOrganisations(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(UserNotFoundException::new);

        return ResponseEntity.ok(organisationService.findByUser(user));
    }

    @GetMapping("/{orgId}")
    public ResponseEntity<?> getOrganisationById(@PathVariable String orgId, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName())
                .orElseThrow(UserNotFoundException::new);

        OrganisationResponseDTO organisationResponse = organisationService.findOrganisationById(user, orgId);

        return ResponseEntity.ok(organisationResponse);
    }


    @PostMapping
    public ResponseEntity<?> createOrganisation(@Valid @RequestBody OrganisationCreateRequestDto request, Authentication authentication) {

       try {
           OrganisationResponseDTO responseDTO = organisationService.createOrganisation(request, authentication);
           return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
       }
       catch (RuntimeException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, Object>() {{
               put("status", "Bad request");
               put("message", "Client error");
               put("statusCode", 400);
           }});
       }
    }

    @PostMapping("/{orgId}/users")
    public ResponseEntity<?> addUserToOrganisation(
            @PathVariable String orgId,
            @Valid @RequestBody AddUserToOrganisationRequestDto request,
            Authentication authentication
    ) {
        OrganisationResponseDTO responseDTO = organisationService.addUserToOrganisation(orgId, request, authentication);
        return ResponseEntity.ok(responseDTO);
    }
}
