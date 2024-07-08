package com.hng.controller;

import com.hng.service.AuthService;
import com.hng.dto.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {


        try {
            UserRegistrationResponse registrationResponse = authService.registerUser(userRegistrationRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, Object>() {{
                put("status", "Bad request");
                put("message", "Registration unsuccessful");
                put("statusCode", 400);
            }});
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        try {

            UserLoginResponse response = authService.authenticateUser(userLoginRequestDto);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<String, Object>() {{
                put("status", "Bad request");
                put("message", "Authentication failed");
                put("statusCode", 401);
            }});
        }
    }
}