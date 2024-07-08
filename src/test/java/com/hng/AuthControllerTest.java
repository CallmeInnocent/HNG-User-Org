package com.hng;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hng.controller.AuthController;
import com.hng.dto.UserRegistrationRequestDto;
import com.hng.entity.User;
import com.hng.exception.DuplicateEmailException;
import com.hng.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterUserSuccessfullyWithDefaultOrganisation() throws Exception {
        // Setup mock user registration response
        when(userService.registerUser(Mockito.any(UserRegistrationRequestDto.class)))
                .thenReturn(createMockUser());

        // Create user registration request DTO
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto("John", "Doe", "john.doe@example.com", "password", null);

        // Perform registration request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // Implement assertions for response body
        String responseBody = result.getResponse().getContentAsString();
        // Example: Assert JSON structure and content
        assertEquals("{\"status\":\"success\",\"message\":\"User registered successfully\"}", responseBody);
    }

    @Test
    @WithMockUser(username = "john.doe@example.com", password = "password")
    public void testLoginUserSuccessfully() throws Exception {
        // Mock the expected behavior of userService.findByEmail
        when(userService.findByEmail("john.doe@example.com"))
                .thenReturn(Optional.of(createMockUser()));

        // Perform login request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"john.doe@example.com\", \"password\": \"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Implement assertions for response body
        String responseBody = result.getResponse().getContentAsString();
        // Example: Assert JSON structure and content
        // assertEquals("{\"status\":\"success\",\"message\":\"User logged in successfully\"}", responseBody);
    }

    @Test
    public void testMissingRequiredFields() throws Exception {
        // Create user registration request DTO with missing fields
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto(null, null, null, null, null);

        // Perform registration request with missing fields
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andReturn();
    }

    @Test
    public void testDuplicateEmailOrUserId() throws Exception {
        // Mock the expected behavior of userService.registerUser to throw DuplicateEmailException
        when(userService.registerUser(Mockito.any(UserRegistrationRequestDto.class)))
                .thenThrow(new DuplicateEmailException("Email already exists"));

        // Create user registration request DTO with duplicate email
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto("John", "Doe", "john.doe@example.com", "password", null);

        // Perform registration request with duplicate email
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andReturn();
    }


    private User createMockUser() {
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password");

        return user;
    }
}