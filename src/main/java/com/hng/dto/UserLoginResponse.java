package com.hng.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginResponse {

    private String status;
    private String message;
    private Data data;


}
