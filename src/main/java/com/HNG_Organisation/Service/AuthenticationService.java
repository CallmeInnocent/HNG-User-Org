package com.HNG_Organisation.Service;
import com.HNG_Organisation.dto.*;
import com.HNG_Organisation.entity.User;
import com.HNG_Organisation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public UserSignUpResponse signup(UserSignUpRequest signUpRequest) {

        UserSignUpResponse userSignUpResponse = new UserSignUpResponse();
        try {

            User user = new User()
                    .setFirstName(signUpRequest.getFirstName())
                    .setLastName(signUpRequest.getLastName())
                    .setEmail(signUpRequest.getEmail())
                    .setPhone(signUpRequest.getPhone())
                    .setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            userRepository.save(user);

            userSignUpResponse.setStatus("Success");
            userSignUpResponse.setMessage("Registration successful");
            userSignUpResponse.setAccessToken(userSignUpResponse.getAccessToken());

            UserData userData = new UserData();

            userData.setFirstName(user.getFirstName());
            userData.setLastName(user.getLastName());
            userData.setEmail(user.getEmail());
            userData.setPhone(user.getPhone());

            userSignUpResponse.setUserData(userData);

            return userSignUpResponse;

        } catch (Exception e) {

            UserSignUpResponse userSignUpResponse1 = new UserSignUpResponse();
            userSignUpResponse1.setStatus(e.getMessage());
            userSignUpResponse1.setMessage("Registration Unsuccessful");
            userSignUpResponse1.setStatusCode(HttpStatusCode.valueOf(400).toString());
        }

        return userSignUpResponse;

    }

    public User authenticate(UserLoginRequest userLoginRequest) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( userLoginRequest.getEmail(),
                        userLoginRequest.getPassword()));

        return userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow();

//        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
//
//        try{
//            //We access the DB using the optional interface.
//            Optional<User> username = userRepository.findByEmail(userLoginRequest.getEmail());
//
//            User userFound = username.get();
//
//            //if we find the userName, we simply fetch the userName data and pass them to the userData class who in
//            //turn sends them over the frontend
//            if (username.isPresent())
//            {
//                UserData userData = new UserData();
//                userData.setId(String.valueOf(userFound.getId()));
//                userData.setFirstName(userFound.getFirstName());
//                userData.setLastName(userFound.getLastName());
//                userData.setEmail(userFound.getEmail());
//                userData.setPhone(userFound.getPhone());
//
//                userLoginResponse.setStatus("Success");
//                userLoginResponse.setMessage("Login successful");
//                userLoginResponse.setUserData(userData);
//
//                return userLoginResponse;
//
//            }
//
//            return  userLoginResponse;
//        }
//
//        catch (Exception e) {
//
//            UserLoginResponse userLoginResponse1 = new UserLoginResponse();
//            userLoginResponse1.setStatus(e.getMessage());
//            userLoginResponse1.setMessage("Authentication Failed");
//            userLoginResponse1.setStatusCode(HttpStatusCode.valueOf(401).toString());
//        }
//
//        return  userLoginResponse;
    }

}
