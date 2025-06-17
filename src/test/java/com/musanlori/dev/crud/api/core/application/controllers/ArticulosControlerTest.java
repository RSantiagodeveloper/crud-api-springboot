package com.musanlori.dev.crud.api.core.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musanlori.dev.crud.api.core.application.models.request.ArticuloRequestModel;
import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.ArticuloResponse;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IArticuloService;
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

class ArticulosControlerTest {

    @InjectMocks
    private ArticulosControler controller;

    @Mock
    private IArticuloService service;

    private MockMvc mockMvc;

    private ArticuloRequestModel requestModel;

    private StringBuilder base;

    private String jsonRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        CatalogosRequestModel catalogosRequestModel = new CatalogosRequestModel();
        catalogosRequestModel.setUuidElement("UUID-1234-1234-12");
        catalogosRequestModel.setIdElement(1);
        catalogosRequestModel.setDescription("DESCRIPTION");

        this.requestModel = new ArticuloRequestModel();
        this.requestModel.setUuid("UUID-1234-5678-90");
        this.requestModel.setNombre("NOMBRE");
        this.requestModel.setDescripcion("DESCRIPTION");
        this.requestModel.setPrecio(9.99);
        this.requestModel.setCalificacion(5.0);
        this.requestModel.setUnidades(1);
        this.requestModel.setEditorial(catalogosRequestModel);
        this.requestModel.setFranquicia(catalogosRequestModel);
        this.requestModel.setFabricante(catalogosRequestModel);
        this.requestModel.setTipo(catalogosRequestModel);
        this.base = new StringBuilder();
        this.base.append("/crud-api");
    }

    @Test
    void createArticuloTest() throws Exception {
        String endpoint = base.append("/articulo").toString();
        jsonRequest = new ObjectMapper().writeValueAsString(new ArticuloRequestModel());
        Mockito.when(service.addNewArticulo(Mockito.any(ArticuloRequestModel.class)))
                .thenReturn(new GeneralResponseService());

        mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void readArticuloTest() throws Exception {
        String endpoint = base.append("/articulos").toString();
        Mockito.when(service.getAllArticulos())
                .thenReturn(List.of(new ArticuloResponse()));

        mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testReadArticuloTest() throws Exception {
        String endpoint = base.append("/articulo").toString();
        Mockito.when(service.getArticulo(Mockito.anyString()))
                .thenReturn(new ArticuloResponse());

        mockMvc.perform(MockMvcRequestBuilders.get(endpoint)
                        .param("uuid", "UUID-1234-1234-123"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateArticuloTest() throws Exception {
        String endpoint = base.append("/articulo").toString();
        jsonRequest = new ObjectMapper().writeValueAsString(new ArticuloRequestModel());
        Mockito.when(service.updateArticulo(Mockito.any(ArticuloRequestModel.class)))
                .thenReturn(new ArticuloResponse());

        mockMvc.perform(MockMvcRequestBuilders.put(endpoint)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteArticuloTest() throws Exception {
        String endpoint = base.append("/articulo").toString();
        Mockito.when(service.deleteArticulo(Mockito.anyString()))
                .thenReturn(new GeneralResponseService());

        mockMvc.perform(MockMvcRequestBuilders.delete(endpoint)
                        .param("uuid", "UUID-1234-1234-123"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}