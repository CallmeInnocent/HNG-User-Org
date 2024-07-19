package com.hng;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.hng.controller.RoleController;
import com.hng.dto.RoleCreationRequestDto;
import com.hng.entity.Organisation;
import com.hng.repository.OrganisationRepository;
import com.hng.repository.PermissionRepository;
import com.hng.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoleController.class)
class RoleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private OrganisationRepository organisationRepository;

    @MockBean
    private PermissionRepository permissionRepository;

    @BeforeEach
    void setUp() {
        Organisation organisation = new Organisation();
        when(organisationRepository.findById(anyString())).thenReturn(Optional.of(organisation));
    }

    @Test
    void testCreateRole_Success() throws Exception {
        RoleCreationRequestDto requestDto = new RoleCreationRequestDto();
        requestDto.setRoleName("ROLE_TEST");
        requestDto.setOrganisationId("ORG123");
        requestDto.setPermissionNames(List.of("READ_PRIVILEGES", "WRITE_PRIVILEGES"));

        mockMvc.perform(post("/api/v1/roles/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateRole_Failure() throws Exception {
        RoleCreationRequestDto requestDto = new RoleCreationRequestDto();
        requestDto.setRoleName("ROLE_TEST");
        requestDto.setOrganisationId("ORG123");
        requestDto.setPermissionNames(List.of("READ_PRIVILEGES", "WRITE_PRIVILEGES"));

        when(organisationRepository.findById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/roles/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
}

