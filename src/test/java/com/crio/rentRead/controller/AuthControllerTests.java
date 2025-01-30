package com.crio.rentRead.controller;

import com.crio.rentRead.exchanges.request.LoginRequest;
import com.crio.rentRead.exchanges.request.UserRequest;
import com.crio.rentRead.exchanges.response.AuthResponse;
import com.crio.rentRead.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class AuthControllerTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void registerTest() throws Exception {
        when(authService.registerUser(ArgumentMatchers.any()))
                .thenReturn(new AuthResponse("User registered successfully"));


        URI uri = new URI("/auth/register");

        MockHttpServletResponse resp = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(uri)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new UserRequest("v1@mail.com", "v@123", "vivek", "boss")))
        ).andReturn().getResponse();


        assertEquals(200, resp.getStatus());
        String expected = objectMapper.writeValueAsString(new AuthResponse("User registered successfully"));
        assertEquals(expected, resp.getContentAsString());

    }

    @Test
    void loginTest() throws Exception {
        when(authService.loginUser(ArgumentMatchers.any()))
                .thenReturn(new AuthResponse("User loggedIn successfully"));

        URI uri = new URI("/auth/login");

        MockHttpServletResponse resp = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(uri)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new LoginRequest("v1@mail.com", "v@123")))
        ).andReturn().getResponse();

        assertEquals(200, resp.getStatus());
        String expected = objectMapper.writeValueAsString(new AuthResponse("User loggedIn successfully"));
        assertEquals(expected, resp.getContentAsString());
    }

}
