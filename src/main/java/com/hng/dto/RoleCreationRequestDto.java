package com.hng.dto;



import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleCreationRequestDto {

    @JsonProperty("role_name")
    @NotBlank(message = "role_name is required")
    @NotNull(message = "role_name is required")
    private String roleName;

    @JsonProperty("organization_id")
    @NotBlank(message = "organization_id is required")
    @NotNull(message = "organization_id is required")
    private String organisationId;

    @JsonProperty("permission_names")
    @NotBlank(message = "permission_names is required")
    @NotNull(message = "permission_names is required")
    private List<String> permissionNames;
}

