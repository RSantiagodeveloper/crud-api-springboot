package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatTiposEntity;
import com.musanlori.dev.crud.api.core.errors.exceptions.LogicServiceConflictException;
import com.musanlori.dev.crud.api.core.errors.exceptions.NotFoundResourceException;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatTiposRepository;
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

class TiposServiceTest {

    @InjectMocks
    private TiposService service;

    @Mock
    private JpaCatTiposRepository repository;

    private Integer UUID;

    private String DESCRIPTION;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.UUID = 1;
        this.DESCRIPTION = "DESCRIPTION";
    }

    @Test
    void addNewTipoTest1() {
        Mockito.when(repository.save(Mockito.any(CatTiposEntity.class)))
                .thenReturn(generateEntity());
        GeneralResponseService response = service.addNewTipo(createRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_MSG, response.getMensaje());
    }

    @Test
    void addNewTipoFailTest1() {
        Mockito.when(repository.save(Mockito.any(CatTiposEntity.class)))
                .thenThrow(RuntimeException.class);
        GeneralResponseService response = service.addNewTipo(createRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.GRAL_ERROR_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.GRAL_ERROR_MSG, response.getMensaje());
    }

    @Test
    void getAllTiposTest1() {
        Mockito.when(repository.findAll()).thenReturn(List.of(generateEntity()));
        List<CatalogoResponseModel> response = service.getAllTipos();
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        response.forEach(model ->
                Assertions.assertEquals(this.DESCRIPTION, model.getDescription()));
    }

    @Test
    void getAllTiposTest2() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
        List<CatalogoResponseModel> response = service.getAllTipos();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void getTipoTest1() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(generateEntity()));
        CatalogoResponseModel response = service.getTipo(this.UUID);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(this.UUID.toString(), response.getId());
        Assertions.assertEquals(this.DESCRIPTION, response.getDescription());
        Assertions.assertInstanceOf(String.class, response.getCreated());
        Assertions.assertInstanceOf(String.class, response.getUpdated());
    }

    @Test
    void getTipoFailTest1() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class,
                () -> service.getTipo(this.UUID));
    }

    @Test
    void getTipoFailTest2() {
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.getTipo(null));
    }

    @Test
    void updateTipoTest1() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(generateEntity()));
        Mockito.when(repository.save(Mockito.any(CatTiposEntity.class))).thenReturn(generateEntity());
        CatalogoResponseModel response = service.updateTipo(createRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(this.UUID.toString(), response.getId());
        Assertions.assertEquals(this.DESCRIPTION, response.getDescription());
        Assertions.assertInstanceOf(String.class, response.getCreated());
        Assertions.assertInstanceOf(String.class, response.getUpdated());
    }

    @Test
    void updateTipoFailTest1() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(generateEntity()));
        Mockito.when(repository.save(Mockito.any(CatTiposEntity.class))).thenThrow(RuntimeException.class);
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.updateTipo(createRequest()));
    }

    @Test
    void updateTipoFailTest2() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class, () -> service.updateTipo(createRequest()));
    }

    @Test
    void updateTipoFailTest3() {
        CatalogosRequestModel request = createRequest();
        request.setIdElement(null);
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.updateTipo(request));
    }

    @Test
    void deleteTipoTest1() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(generateEntity()));
        Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());
        GeneralResponseService response = service.deleteTipo(this.UUID);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_MSG, response.getMensaje());
    }

    @Test
    void deleteTipoFailTest1() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(generateEntity()));
        Mockito.doThrow(RuntimeException.class).when(repository).deleteById(Mockito.anyInt());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.deleteTipo(this.UUID));
    }

    @Test
    void deleteTipoFailTest2() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class, () -> service.deleteTipo(this.UUID));
    }

    @Test
    void deleteTipoFailTest3() {
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.deleteTipo(null));
    }

    private CatalogosRequestModel createRequest() {
        CatalogosRequestModel requestModel = new CatalogosRequestModel();
        requestModel.setIdElement(this.UUID);
        requestModel.setDescription(this.DESCRIPTION);
        return requestModel;
    }

    private CatTiposEntity generateEntity() {
        CatTiposEntity entity = new CatTiposEntity();
        entity.setIdTipo(this.UUID);
        entity.setDescripcion(this.DESCRIPTION);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        return entity;
    }
}