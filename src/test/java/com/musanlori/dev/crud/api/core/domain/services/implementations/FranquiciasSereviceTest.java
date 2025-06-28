package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatFranquiciasEntity;
import com.musanlori.dev.crud.api.core.errors.exceptions.LogicServiceConflictException;
import com.musanlori.dev.crud.api.core.errors.exceptions.NotFoundResourceException;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatFranquiciasEntity;
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

class FranquiciasSereviceTest {

    @InjectMocks
    private FranquiciasSerevice service;

    @Mock
    private JpaCatFranquiciasEntity repository;

    private String UUID;

    private String DESCRIPTION;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.UUID = "UUID-1234-5678-90";
        this.DESCRIPTION = "DESCRIPTION";
    }

    @Test
    void addNewFranquiciaTest1() {
        Mockito.when(repository.save(Mockito.any(CatFranquiciasEntity.class)))
                .thenReturn(generateEntity());
        GeneralResponseService response = service.addNewFranquicia(createRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_MSG, response.getMensaje());
    }

    @Test
    void addNewFranquiciaFailTest1() {
        Mockito.when(repository.save(Mockito.any(CatFranquiciasEntity.class)))
                .thenThrow(RuntimeException.class);
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.addNewFranquicia(createRequest()));
    }

    @Test
    void getAllFranquiciasTest1() {
        Mockito.when(repository.findAll()).thenReturn(List.of(generateEntity()));
        List<CatalogoResponseModel> response = service.getAllFranquicias();
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        response.forEach(model ->
                Assertions.assertEquals(this.DESCRIPTION, model.getDescription()));
    }

    @Test
    void getAllFranquiciasTest2() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
        List<CatalogoResponseModel> response = service.getAllFranquicias();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void getFranquiciaTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        CatalogoResponseModel response = service.getFranquicia(this.UUID);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(this.UUID, response.getId());
        Assertions.assertEquals(this.DESCRIPTION, response.getDescription());
        Assertions.assertInstanceOf(String.class, response.getCreated());
        Assertions.assertInstanceOf(String.class, response.getUpdated());
    }

    @Test
    void getFranquiciaFailTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class,
                () -> service.getFranquicia(this.UUID));
    }

    @Test
    void getFranquiciaFailTest2() {
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.getFranquicia(null));
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.getFranquicia(""));
    }

    @Test
    void updateFranquiciaTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.when(repository.save(Mockito.any(CatFranquiciasEntity.class))).thenReturn(generateEntity());
        CatalogoResponseModel response = service.updateFranquicia(createRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(this.UUID, response.getId());
        Assertions.assertEquals(this.DESCRIPTION, response.getDescription());
        Assertions.assertInstanceOf(String.class, response.getCreated());
        Assertions.assertInstanceOf(String.class, response.getUpdated());
    }

    @Test
    void updateFranquiciaFailTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.when(repository.save(Mockito.any(CatFranquiciasEntity.class))).thenThrow(RuntimeException.class);
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.updateFranquicia(createRequest()));
    }

    @Test
    void updateFranquiciaFailTest2() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class, () -> service.updateFranquicia(createRequest()));
    }

    @Test
    void updateFranquiciaFailTest3() {
        CatalogosRequestModel request = createRequest();
        request.setUuidElement(null);
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.updateFranquicia(request));
        request.setUuidElement("");
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.updateFranquicia(request));
    }

    @Test
    void deleteFranquiciaTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.doNothing().when(repository).deleteById(Mockito.anyString());
        GeneralResponseService response = service.deleteFranquicia(this.UUID);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_MSG, response.getMensaje());
    }

    @Test
    void deleteFranquiciaFailTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.doThrow(RuntimeException.class).when(repository).deleteById(Mockito.anyString());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.deleteFranquicia(this.UUID));
    }

    @Test
    void deleteFranquiciaFailTest2() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class, () -> service.deleteFranquicia(this.UUID));
    }

    @Test
    void deleteFranquiciaFailTest3() {
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.deleteFranquicia(null));
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.deleteFranquicia(""));
    }

    private CatalogosRequestModel createRequest() {
        CatalogosRequestModel requestModel = new CatalogosRequestModel();
        requestModel.setUuidElement(this.UUID);
        requestModel.setDescription(this.DESCRIPTION);
        return requestModel;
    }

    private CatFranquiciasEntity generateEntity() {
        CatFranquiciasEntity entity = new CatFranquiciasEntity();
        entity.setIdFranquicia(this.UUID);
        entity.setDescripcion(this.DESCRIPTION);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        return entity;
    }
}