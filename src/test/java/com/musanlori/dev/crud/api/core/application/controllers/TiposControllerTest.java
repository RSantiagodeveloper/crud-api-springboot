package com.musanlori.dev.crud.api.core.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.services.definitions.ITiposService;
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

class TiposControllerTest {

    @InjectMocks
    private TiposController controller;

    @Mock
    private ITiposService service;

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
    void createTipo() throws Exception {
        String endpoint = base.append("/tipo").toString();
        jsonRequest = new ObjectMapper().writeValueAsString(catalogosRequestModel);
        Mockito.when(service.addNewTipo(Mockito.any(CatalogosRequestModel.class)))
                .thenReturn(new GeneralResponseService());

        mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void readTipo() throws Exception {
        String endpoint = base.append("/tipos").toString();
        Mockito.when(service.getAllTipos())
                .thenReturn(List.of(new CatalogoResponseModel()));

        mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testReadTipo() throws Exception {
        String endpoint = base.append("/tipo").toString();
        Mockito.when(service.getTipo(Mockito.anyInt()))
                .thenReturn(new CatalogoResponseModel());

        mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                        .param("uuid", "123"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateTipo() throws Exception {
        String endpoint = base.append("/tipo").toString();
        jsonRequest = new ObjectMapper().writeValueAsString(catalogosRequestModel);
        Mockito.when(service.updateTipo(Mockito.any(CatalogosRequestModel.class)))
                .thenReturn(new CatalogoResponseModel());

        mockMvc.perform(MockMvcRequestBuilders.put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteTipo() throws Exception {
        String endpoint = base.append("/tipo").toString();
        Mockito.when(service.deleteTipo(Mockito.anyInt()))
                .thenReturn(new GeneralResponseService());

        mockMvc.perform(MockMvcRequestBuilders.delete(endpoint)
                        .param("uuid", "123"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}