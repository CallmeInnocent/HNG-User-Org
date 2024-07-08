package com.hng.dto;

import com.hng.entity.Organisation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationResponseDTO {

    private String status;
    private String message;
    private OrganisationData data;

}
