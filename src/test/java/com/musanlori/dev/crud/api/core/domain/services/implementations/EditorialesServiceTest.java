package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.CatalogoResponseModel;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatEditorialesEntity;
import com.musanlori.dev.crud.api.core.errors.exceptions.LogicServiceConflictException;
import com.musanlori.dev.crud.api.core.errors.exceptions.NotFoundResourceException;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatEditorialesRepository;
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

class EditorialesServiceTest {

    @InjectMocks
    private EditorialesService service;

    @Mock
    private JpaCatEditorialesRepository repository;

    private String UUID;

    private String DESCRIPTION;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.UUID = "UUID-1234-5678-90";
        this.DESCRIPTION = "DESCRIPTION";
    }

    @Test
    void addNewEditorialTest1() {
        Mockito.when(repository.save(Mockito.any(CatEditorialesEntity.class)))
                .thenReturn(generateEntity());
        GeneralResponseService response = service.addNewEditorial(createRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_MSG, response.getMensaje());
    }

    @Test
    void addNewEditorialFailTest1() {
        Mockito.when(repository.save(Mockito.any(CatEditorialesEntity.class)))
                .thenThrow(RuntimeException.class);
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.addNewEditorial(createRequest()));
    }

    @Test
    void getAllEditorialesTest1() {
        Mockito.when(repository.findAll()).thenReturn(List.of(generateEntity()));
        List<CatalogoResponseModel> response = service.getAllEditoriales();
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        response.forEach(model ->
                Assertions.assertEquals(this.DESCRIPTION, model.getDescription()));
    }

    @Test
    void getAllEditorialesTest2() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
        List<CatalogoResponseModel> response = service.getAllEditoriales();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void getEditorialTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        CatalogoResponseModel response = service.getEditorial(this.UUID);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(this.UUID, response.getId());
        Assertions.assertEquals(this.DESCRIPTION, response.getDescription());
        Assertions.assertInstanceOf(String.class, response.getCreated());
        Assertions.assertInstanceOf(String.class, response.getUpdated());
    }

    @Test
    void getEditorialFailTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class,
                () -> service.getEditorial(this.UUID));
    }

    @Test
    void getEditorialFailTest2() {
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.getEditorial(null));
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.getEditorial(""));
    }

    @Test
    void updateEditorialTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.when(repository.save(Mockito.any(CatEditorialesEntity.class))).thenReturn(generateEntity());
        CatalogoResponseModel response = service.updateEditorial(createRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(this.UUID, response.getId());
        Assertions.assertEquals(this.DESCRIPTION, response.getDescription());
        Assertions.assertInstanceOf(String.class, response.getCreated());
        Assertions.assertInstanceOf(String.class, response.getUpdated());
    }

    @Test
    void updateEditorialFailTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.when(repository.save(Mockito.any(CatEditorialesEntity.class))).thenThrow(RuntimeException.class);
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.updateEditorial(createRequest()));
    }

    @Test
    void updateEditorialFailTest2() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class, () -> service.updateEditorial(createRequest()));
    }

    @Test
    void updateEditorialFailTest3() {
        CatalogosRequestModel request = createRequest();
        request.setUuidElement(null);
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.updateEditorial(request));
        request.setUuidElement("");
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.updateEditorial(request));
    }

    @Test
    void deleteEditorialTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.doNothing().when(repository).deleteById(Mockito.anyString());
        GeneralResponseService response = service.deleteEditorial(this.UUID);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_MSG, response.getMensaje());
    }

    @Test
    void deleteEditorialFailTest1() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.doThrow(RuntimeException.class).when(repository).deleteById(Mockito.anyString());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.deleteEditorial(this.UUID));
    }

    @Test
    void deleteEditorialFailTest2() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class, () -> service.deleteEditorial(this.UUID));
    }

    @Test
    void deleteEditorialFailTest3() {
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.deleteEditorial(null));
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.deleteEditorial(""));
    }

    private CatalogosRequestModel createRequest() {
        CatalogosRequestModel requestModel = new CatalogosRequestModel();
        requestModel.setUuidElement(this.UUID);
        requestModel.setDescription(this.DESCRIPTION);
        return requestModel;
    }

    private CatEditorialesEntity generateEntity() {
        CatEditorialesEntity entity = new CatEditorialesEntity();
        entity.setIdEditorial(this.UUID);
        entity.setDescripcion(this.DESCRIPTION);
        entity.setCreatedAt(new Date());
        entity.setUpdatedAt(new Date());
        return entity;
    }
}