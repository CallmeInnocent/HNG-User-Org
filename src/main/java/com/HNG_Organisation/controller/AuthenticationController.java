package com.HNG_Organisation.controller;

import com.HNG_Organisation.dto.UserLoginRequest;
import com.HNG_Organisation.dto.UserLoginResponse;
import com.HNG_Organisation.dto.UserSignUpRequest;
import com.HNG_Organisation.dto.UserSignUpResponse;
import com.HNG_Organisation.entity.User;
import com.HNG_Organisation.Service.AuthenticationService;
import com.HNG_Organisation.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserSignUpResponse> register(@RequestBody UserSignUpRequest userSignUpRequest) {
        UserSignUpResponse registeredUser = authenticationService.signup(userSignUpRequest);

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> authenticate(@RequestBody UserLoginRequest userLoginRequest) {
        User authenticatedUser = authenticationService.authenticate(userLoginRequest);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        UserLoginResponse userLoginResponse = new UserLoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(userLoginResponse);

    }

}