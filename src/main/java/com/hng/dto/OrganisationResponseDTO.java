package com.hng.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hng.entity.Organisation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganisationResponseDTO {

    private String status;
    private String message;
    private OrganisationData data;

}
