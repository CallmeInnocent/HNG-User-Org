package com.HNG_Organisation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSignUpResponse {

    private String status;
    private String statusCode;
    private String message;
    private UserData userData;
    private String accessToken;

    public UserSignUpResponse setToken(String token) {
        this.accessToken = token;
        return this;
    }


}
