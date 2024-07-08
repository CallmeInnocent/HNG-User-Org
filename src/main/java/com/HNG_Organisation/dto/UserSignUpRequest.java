package com.HNG_Organisation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;


}
