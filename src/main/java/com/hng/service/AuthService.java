package com.hng.service;

import com.hng.dto.*;
import com.hng.entity.User;
import com.hng.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private Validator validator;

    public UserRegistrationResponse registerUser(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {


        try {
            User registeredUser = userService.registerUser(userRegistrationRequestDto);
            organisationService.createOrganisation(registeredUser);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRegistrationRequestDto.getEmail(), userRegistrationRequestDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(userDetails);


            UserDetail userInfo = new UserDetail();
            userInfo.setUserId(registeredUser.getUserId());
            userInfo.setFirstName(registeredUser.getFirstName());
            userInfo.setLastName(registeredUser.getLastName());
            userInfo.setEmail(registeredUser.getEmail());
            userInfo.setPhone(registeredUser.getPhone());

            Data data = new Data();
            data.setUser(userInfo);
            data.setAccessToken(jwtToken);

            UserRegistrationResponse registrationResponse = new UserRegistrationResponse();
            registrationResponse.setStatus("success");
            registrationResponse.setMessage("Registration successful");
            registrationResponse.setData(data);

            return registrationResponse;
        } catch (Exception e) {
            throw new RuntimeException("Registration unsuccessful", e);
        }
    }


    public UserLoginResponse authenticateUser(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(userDetails);

            User user = userService.findByEmail(userLoginRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

            UserDetail userDetail = new UserDetail();
            userDetail.setUserId(user.getUserId());
            userDetail.setFirstName(user.getFirstName());
            userDetail.setLastName(user.getLastName());
            userDetail.setEmail(user.getEmail());
            userDetail.setPhone(user.getPhone());

            Data data = new Data();
            data.setUser(userDetail);
            data.setAccessToken(jwtToken);

            UserLoginResponse loginResponse = new UserLoginResponse();
            loginResponse.setStatus("success");
            loginResponse.setMessage("Login successful");
            loginResponse.setData(data);


            return loginResponse;
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }
}