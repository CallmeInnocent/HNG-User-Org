package com.hng;



import com.hng.controller.RoleController;
import com.hng.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleController.class)
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        // Set up any required beans or configurations
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateRole_WithAdminRole() throws Exception {
        mockMvc.perform(post("/api/v1/roles/create"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testCreateRole_WithoutAdminRole() throws Exception {
        mockMvc.perform(post("/api/v1/roles/create"))
                .andExpect(status().isForbidden());
    }
}

