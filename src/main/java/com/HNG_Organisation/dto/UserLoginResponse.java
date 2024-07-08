package com.HNG_Organisation.dto;

import com.HNG_Organisation.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginResponse {

    private String status;
    private String statusCode;
    private String message;
    private String jwt;
    private UserData userData;
    private String accessToken;
    private long expiresIn;

    public UserLoginResponse setToken(String token) {
        this.accessToken = token;
        return this;
    }

    public UserLoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public UserLoginResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public UserLoginResponse setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public UserLoginResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public UserLoginResponse setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }

    public UserLoginResponse setUserData(UserData userData) {
        this.userData = userData;
        return this;
    }
}
