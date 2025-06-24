package com.musanlori.dev.crud.api.core.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musanlori.dev.crud.api.core.application.models.request.UserRequest;
import com.musanlori.dev.crud.api.core.application.models.response.UserResponse;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

class UsersControllerTest {

    @InjectMocks
    private UsersController controller;

    @Mock
    private IUserService service;

    private MockMvc mockMvc;

    private StringBuilder base;

    private String jsonRequest;

    private UserRequest request;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        this.request = new UserRequest();
        this.request.setUsername("USERNAME");
        this.request.setPassword("PASSWD");
        this.request.setAdmin(true);

        this.base = new StringBuilder();
        this.base.append("/auth");
    }

    @Test
    void listAllControllerTest() throws Exception {
        String endpoint = base.append("/get-all").toString();
        Mockito.when(service.findAll()).thenReturn(List.of(new UserResponse()));

        mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createUserControllerTest() throws Exception {
        String endpoint = base.append("/add-user").toString();
        jsonRequest = new ObjectMapper().writeValueAsString(request);
        Mockito.when(service.save(Mockito.any(UserRequest.class)))
                .thenReturn(new UserResponse());

        mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}