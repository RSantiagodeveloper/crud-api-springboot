package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatFabricantesEntity;
import com.musanlori.dev.crud.api.core.errors.exceptions.LogicServiceConflictException;
import com.musanlori.dev.crud.api.core.errors.exceptions.NotFoundResourceException;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatFabricanteRepository;
import com.musanlori.dev.crud.api.core.util.ErrorServiceMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class FabricantesServiceTest {

    @InjectMocks
    private FabricantesService service;

    @Mock
    private JpaCatFabricanteRepository repository;

    private String UUID;

    private String DESCRIPTION;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.UUID = "UUID-1234-5678-90";
        this.DESCRIPTION = "DESCRIPTION";
    }

    @Test
    void addNewFabricanteTest1() {
        Mockito.when(repository.save(Mockito.any(CatFabricantesEntity.class)))
                .thenReturn(generateEntity());
        GeneralResponseService response = service.addNewFabricante(createRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_MSG, response.getMensaje());
    }

    @Test
    void addNewFabricanteFailTest1() {
        Mockito.when(repository.save(Mockito.any(CatFabricantesEntity.class)))
                .thenThrow(RuntimeException.class);
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.addNewFabricante(createRequest()));
    }

    @Test
    void getAllFabricantesTest1() {
        Mockito.when(repository.findAll()).thenReturn(List.of(generateEntity()));
        List<CatalogoResponseModel> response = service.getAllFabricantes();
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        response.forEach(model ->
                Assertions.assertEquals(this.DESCRIPTION, model.getDescription()));
    }

    @Test
    void getAllFabricantesTest2() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
        List<CatalogoResponseModel> response = service.getAllFabricantes();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void getFabricanteTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        CatalogoResponseModel response = service.getFabricante(this.UUID);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(this.UUID, response.getId());
        Assertions.assertEquals(this.DESCRIPTION, response.getDescription());
        Assertions.assertInstanceOf(String.class, response.getCreated());
        Assertions.assertInstanceOf(String.class, response.getUpdated());
    }

    @Test
    void getFabricanteFailTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class,
                () -> service.getFabricante(this.UUID));
    }

    @Test
    void getFabricanteFailTest2() {
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.getFabricante(null));
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.getFabricante(""));
    }

    @Test
    void updateFabricanteTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.when(repository.save(Mockito.any(CatFabricantesEntity.class))).thenReturn(generateEntity());
        CatalogoResponseModel response = service.updateFabricante(createRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(this.UUID, response.getId());
        Assertions.assertEquals(this.DESCRIPTION, response.getDescription());
        Assertions.assertInstanceOf(String.class, response.getCreated());
        Assertions.assertInstanceOf(String.class, response.getUpdated());
    }

    @Test
    void updateFabricanteFailTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.when(repository.save(Mockito.any(CatFabricantesEntity.class))).thenThrow(RuntimeException.class);
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.updateFabricante(createRequest()));
    }

    @Test
    void updateFabricanteFailTest2() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class, () -> service.updateFabricante(createRequest()));
    }

    @Test
    void updateFabricanteFailTest3() {
        CatalogosRequestModel request = createRequest();
        request.setUuidElement(null);
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.updateFabricante(request));
        request.setUuidElement("");
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.updateFabricante(request));
    }

    @Test
    void deleteFabricanteTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.doNothing().when(repository).deleteById(Mockito.anyString());
        GeneralResponseService response = service.deleteFabricante(this.UUID);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_MSG, response.getMensaje());
    }

    @Test
    void deleteFabricanteFailTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.doThrow(RuntimeException.class).when(repository).deleteById(Mockito.anyString());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.deleteFabricante(this.UUID));
    }

    @Test
    void deleteFabricanteFailTest2() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class, () -> service.deleteFabricante(this.UUID));
    }

    @Test
    void deleteFabricanteFailTest3() {
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.deleteFabricante(null));
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.deleteFabricante(""));
    }

    private CatalogosRequestModel createRequest() {
        CatalogosRequestModel requestModel = new CatalogosRequestModel();
        requestModel.setUuidElement(this.UUID);
        requestModel.setDescription(this.DESCRIPTION);
        return requestModel;
    }

    private CatFabricantesEntity generateEntity() {
        CatFabricantesEntity entity = new CatFabricantesEntity();
        entity.setIdFabricante(this.UUID);
        entity.setDescripcion(this.DESCRIPTION);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        return entity;
    }
}