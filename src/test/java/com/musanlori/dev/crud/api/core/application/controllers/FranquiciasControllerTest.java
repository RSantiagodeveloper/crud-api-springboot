package com.musanlori.dev.crud.api.core.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IFranquiciasService;
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

class FranquiciasControllerTest {

    @InjectMocks
    private FranquiciasController controller;

    @Mock
    private IFranquiciasService service;

    private MockMvc mockMvc;

    private StringBuilder base;

    private String jsonRequest;

    private CatalogosRequestModel catalogosRequestModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        this.catalogosRequestModel = new CatalogosRequestModel();
        this.catalogosRequestModel.setUuidElement("UUID-1234-1234-12");
        this.catalogosRequestModel.setIdElement(1);
        this.catalogosRequestModel.setDescription("DESCRIPTION");
        this.base = new StringBuilder();
        this.base.append("/crud-api");
    }

    @Test
    void createFranquicia() throws Exception {
        String endpoint = base.append("/franquicia").toString();
        jsonRequest = new ObjectMapper().writeValueAsString(catalogosRequestModel);
        Mockito.when(service.addNewFranquicia(Mockito.any(CatalogosRequestModel.class)))
                .thenReturn(new GeneralResponseService());

        mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void readFranquicia() throws Exception {
        String endpoint = base.append("/franquicias").toString();
        Mockito.when(service.getAllFranquicias())
                .thenReturn(List.of(new CatalogoResponseModel()));

        mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testReadFranquicia() throws Exception {
        String endpoint = base.append("/franquicia").toString();
        Mockito.when(service.getFranquicia(Mockito.anyString()))
                .thenReturn(new CatalogoResponseModel());

        mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                        .param("uuid", "UUID-1234-1234-123"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateFranquicia() throws Exception {
        String endpoint = base.append("/franquicia").toString();
        jsonRequest = new ObjectMapper().writeValueAsString(catalogosRequestModel);
        Mockito.when(service.updateFranquicia(Mockito.any(CatalogosRequestModel.class)))
                .thenReturn(new CatalogoResponseModel());

        mockMvc.perform(MockMvcRequestBuilders.put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteFranquicia() throws Exception {
        String endpoint = base.append("/franquicia").toString();
        Mockito.when(service.deleteFranquicia(Mockito.anyString()))
                .thenReturn(new GeneralResponseService());

        mockMvc.perform(MockMvcRequestBuilders.delete(endpoint)
                        .param("uuid", "UUID-1234-1234-123"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}