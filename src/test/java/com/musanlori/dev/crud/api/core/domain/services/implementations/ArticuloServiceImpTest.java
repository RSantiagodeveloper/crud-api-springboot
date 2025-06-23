package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.application.models.request.ArticuloRequestModel;
import com.musanlori.dev.crud.api.core.application.models.request.CatalogosRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.ArticuloResponse;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.model.entity.ArticulosEntity;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatEditorialesEntity;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatFabricantesEntity;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatFranquiciasEntity;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatTiposEntity;
import com.musanlori.dev.crud.api.core.errors.exceptions.LogicServiceConflictException;
import com.musanlori.dev.crud.api.core.errors.exceptions.NotFoundResourceException;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaArticulosRepository;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatEditorialesRepository;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatFabricanteRepository;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatFranquiciasEntity;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatTiposRepository;
import com.musanlori.dev.crud.api.core.util.ErrorServiceMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class ArticuloServiceImpTest {

    @InjectMocks
    private ArticuloServiceImp service;

    @Mock
    private JpaArticulosRepository jpaArticulosRepository;

    @Mock
    private JpaCatEditorialesRepository jpaCatEditorialesRepository;

    @Mock
    private JpaCatFabricanteRepository jpaCatFabricanteRepository;

    @Mock
    private JpaCatFranquiciasEntity jpaCatFranquiciasEntity;

    @Mock
    private JpaCatTiposRepository jpaCatTiposRepository;

    private Integer id;

    private String uuid;

    private String name;

    private String description;

    private Double price;

    private Double calif;

    private Integer unities;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.uuid = "UUID-1234-5678-90";
        this.name = "NAME";
        this.description = "DESCRIPTION";
        this.id = 1;
        this.price = 99.99;
        this.calif = 5.0;
        this.unities = 100;
    }

    @Test
    void addNewArticuloTest1() {
        Mockito.when(jpaCatTiposRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(generateTipo()));
        Mockito.when(jpaCatEditorialesRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEditorial()));
        Mockito.when(jpaCatFabricanteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateFabricante()));
        Mockito.when(jpaCatFranquiciasEntity.findById(Mockito.anyString())).thenReturn(Optional.of(generateFranquicia()));
        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenReturn(generateEntity());
        GeneralResponseService response = service.addNewArticulo(createRequest(true));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_MSG, response.getMensaje());
    }

    @Test
    void addNewArticuloTest2() {
        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenReturn(generateEntity());
        GeneralResponseService response = service.addNewArticulo(createRequest(false));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_MSG, response.getMensaje());
    }

    @Test
    void addNewArticuloFailTest1() {
        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenThrow(RuntimeException.class);
        GeneralResponseService response = service.addNewArticulo(createRequest(false));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.GRAL_ERROR_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.GRAL_ERROR_MSG, response.getMensaje());
    }

    @ParameterizedTest
    @CsvSource({
            "true, uuid",
            "false, ",
            "false, ''"
    })
    void addNewArticuloFailTest2(boolean flag, String uuid) {

        ArticuloRequestModel request = createRequest(false);
        CatalogosRequestModel model = new CatalogosRequestModel();
        model.setUuidElement(uuid);
        model.setDescription(this.description);
        request.setFranquicia(model);

        if (flag) {
            Mockito.when(jpaCatFranquiciasEntity.findById(Mockito.anyString())).thenReturn(Optional.empty());
        }

        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenReturn(generateEntity());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.addNewArticulo(request));
    }

    @ParameterizedTest
    @CsvSource({
            "true, uuid",
            "false, ",
            "false, ''"
    })
    void addNewArticuloFailTest3(boolean flag, String uuid) {

        ArticuloRequestModel request = createRequest(false);
        CatalogosRequestModel model = new CatalogosRequestModel();
        model.setUuidElement(uuid);
        model.setDescription(this.description);
        request.setEditorial(model);

        if (flag) {
            Mockito.when(jpaCatEditorialesRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        }

        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenReturn(generateEntity());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.addNewArticulo(request));
    }

    @ParameterizedTest
    @CsvSource({
            "true, uuid",
            "false, ",
            "false, ''"
    })
    void addNewArticuloFailTest4(boolean flag, String uuid) {

        ArticuloRequestModel request = createRequest(false);
        CatalogosRequestModel model = new CatalogosRequestModel();
        model.setUuidElement(uuid);
        model.setDescription(this.description);
        request.setFabricante(model);

        if (flag) {
            Mockito.when(jpaCatFabricanteRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        }

        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenReturn(generateEntity());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.addNewArticulo(request));
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void addNewArticuloFailTest5(boolean flag) {

        ArticuloRequestModel request = createRequest(false);
        CatalogosRequestModel model = new CatalogosRequestModel();
        model.setIdElement((flag) ? this.id: null);
        model.setDescription(this.description);
        request.setTipo(model);

        if (flag) {
            Mockito.when(jpaCatTiposRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        }

        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenReturn(generateEntity());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.addNewArticulo(request));
    }

    @Test
    void getAllArticulosTest1() {
        Mockito.when(jpaArticulosRepository.findAll()).thenReturn(List.of(generateEntity()));
        List<ArticuloResponse> response = service.getAllArticulos();
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());

        response.forEach(art ->
                Assertions.assertInstanceOf(String.class, art.getUuid()));
    }

    @Test
    void getAllArticulosTest2() {
        Mockito.when(jpaArticulosRepository.findAll()).thenReturn(new ArrayList<>());
        List<ArticuloResponse> response = service.getAllArticulos();
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void getArticuloTest1() {
        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        ArticuloResponse response = service.getArticulo(this.uuid);
        Assertions.assertNotNull(response);
        Assertions.assertInstanceOf(String.class, response.getUuid());
        Assertions.assertEquals(this.uuid, response.getUuid());
    }

    @Test
    void getArticuloFailTest1() {
        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class, () -> service.getArticulo(this.uuid));
    }

    @Test
    void getArticuloFailTest2() {
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.getArticulo(null));
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.getArticulo(""));
    }

    @Test
    void updateArticuloTest1() {
        ArticulosEntity entity = generateEntity();
        entity.setEditorial(null);
        entity.setFabricante(null);
        entity.setFranquicia(null);
        entity.setTipo(null);

        Mockito.when(jpaCatTiposRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(generateTipo()));
        Mockito.when(jpaCatEditorialesRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEditorial()));
        Mockito.when(jpaCatFabricanteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateFabricante()));
        Mockito.when(jpaCatFranquiciasEntity.findById(Mockito.anyString())).thenReturn(Optional.of(generateFranquicia()));
        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenReturn(entity);
        ArticuloResponse response = service.updateArticulo(createRequest(true));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(this.uuid, response.getUuid());
        Assertions.assertInstanceOf(String.class, response.getNombre());
        Assertions.assertInstanceOf(String.class, response.getDescripcion());
        Assertions.assertNull(response.getTipo());
        Assertions.assertNull(response.getFabricante());
        Assertions.assertNull(response.getFranquicia());
        Assertions.assertNull(response.getEditorial());
        Assertions.assertInstanceOf(String.class, response.getUpdate());
    }

    @Test
    void updateArticuloTest2() {
        ArticuloRequestModel request = new ArticuloRequestModel();
        request.setUuid(this.uuid);

        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenReturn(generateEntity());
        ArticuloResponse response = service.updateArticulo(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(this.uuid, response.getUuid());
        Assertions.assertInstanceOf(String.class, response.getNombre());
        Assertions.assertInstanceOf(String.class, response.getDescripcion());
        Assertions.assertInstanceOf(String.class, response.getTipo());
        Assertions.assertInstanceOf(String.class, response.getFabricante());
        Assertions.assertInstanceOf(String.class, response.getFranquicia());
        Assertions.assertInstanceOf(String.class, response.getEditorial());
        Assertions.assertInstanceOf(String.class, response.getUpdate());
    }

    @Test
    void updateArticuloFailTest1() {
        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenThrow(RuntimeException.class);
        Assertions.assertThrows(LogicServiceConflictException.class,
                () -> service.updateArticulo(createRequest(false)));
    }

    @ParameterizedTest
    @CsvSource({
            "true, uuid",
            "false, ",
            "false, ''"
    })
    void updateArticuloFailTest2(boolean flag, String uuid) {

        ArticuloRequestModel request = createRequest(false);
        CatalogosRequestModel model = new CatalogosRequestModel();
        model.setUuidElement(uuid);
        model.setDescription(this.description);
        request.setFranquicia(model);

        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        if (flag) {
            Mockito.when(jpaCatFranquiciasEntity.findById(Mockito.anyString())).thenReturn(Optional.empty());
        }

        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenReturn(generateEntity());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.updateArticulo(request));
    }

    @ParameterizedTest
    @CsvSource({
            "true, uuid",
            "false, ",
            "false, ''"
    })
    void updateArticuloFailTest3(boolean flag, String uuid) {

        ArticuloRequestModel request = createRequest(false);
        CatalogosRequestModel model = new CatalogosRequestModel();
        model.setUuidElement(uuid);
        model.setDescription(this.description);
        request.setEditorial(model);

        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        if (flag) {
            Mockito.when(jpaCatEditorialesRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        }

        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenReturn(generateEntity());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.updateArticulo(request));
    }

    @ParameterizedTest
    @CsvSource({
            "true, uuid",
            "false, ",
            "false, ''"
    })
    void updateArticuloFailTest4(boolean flag, String uuid) {

        ArticuloRequestModel request = createRequest(false);
        CatalogosRequestModel model = new CatalogosRequestModel();
        model.setUuidElement(uuid);
        model.setDescription(this.description);
        request.setFabricante(model);

        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        if (flag) {
            Mockito.when(jpaCatFabricanteRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        }

        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenReturn(generateEntity());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.updateArticulo(request));
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void updateArticuloFailTest5(boolean flag) {

        ArticuloRequestModel request = createRequest(false);
        CatalogosRequestModel model = new CatalogosRequestModel();
        model.setIdElement((flag) ? this.id: null);
        model.setDescription(this.description);
        request.setTipo(model);

        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        if (flag) {
            Mockito.when(jpaCatTiposRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        }

        Mockito.when(jpaArticulosRepository.save(Mockito.any(ArticulosEntity.class))).thenReturn(generateEntity());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.updateArticulo(request));
    }

    @Test
    void updateArticuloFailTest6() {
        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class,
                () -> service.updateArticulo(createRequest(false)));
    }

    @Test
    void updateArticuloFailTest7() {
        ArticuloRequestModel request = new ArticuloRequestModel();
        request.setUuid(null);
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.updateArticulo(request));
        request.setUuid("");
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.updateArticulo(request));
    }

    @Test
    void deleteArticuloTest1() {
        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.doNothing().when(jpaArticulosRepository).deleteById(Mockito.anyString());
        GeneralResponseService response = service.deleteArticulo(this.uuid);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_CODE, response.getCodigo());
        Assertions.assertEquals(ErrorServiceMessages.OPERATION_SUCCESS_MSG, response.getMensaje());
    }

    @Test
    void deleteArticuloFailTest1() {
        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.of(generateEntity()));
        Mockito.doThrow(RuntimeException.class).when(jpaArticulosRepository).deleteById(Mockito.anyString());
        Assertions.assertThrows(LogicServiceConflictException.class, () -> service.deleteArticulo(this.uuid));
    }

    @Test
    void deleteArticuloFailTest2() {
        Mockito.when(jpaArticulosRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class, () -> service.deleteArticulo(this.uuid));
    }

    @Test
    void deleteArticuloFailTest3() {
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.deleteArticulo(null));
        Assertions.assertThrows(RequestDataNotValidException.class, () -> service.deleteArticulo(""));
    }

    private ArticuloRequestModel createRequest(boolean setCats) {
        ArticuloRequestModel request = new ArticuloRequestModel();
        request.setUuid(this.uuid);
        request.setNombre(this.name);
        request.setDescripcion(this.description);
        request.setPrecio(99.99);
        request.setCalificacion(5.0);
        request.setUnidades(88);

        if (setCats) {
            CatalogosRequestModel catalogo = new CatalogosRequestModel();
            catalogo.setIdElement(this.id);;
            catalogo.setUuidElement(this.uuid);
            catalogo.setDescription(this.description);
            request.setEditorial(catalogo);
            request.setFabricante(catalogo);
            request.setFranquicia(catalogo);
            request.setTipo(catalogo);
        }

        return request;
    }

    private ArticulosEntity generateEntity() {

        ArticulosEntity model = new ArticulosEntity();
        model.setIdArticulo(this.uuid);
        model.setNombre(this.name);
        model.setDescripcion(this.description);
        model.setPrecio(this.price);
        model.setCalificacion(this.calif);
        model.setUnidades(this.unities);
        model.setEditorial(generateEditorial());
        model.setFabricante(generateFabricante());
        model.setFranquicia(generateFranquicia());
        model.setTipo(generateTipo());
        model.setCreatedAt(new Date());
        model.setUpdatedAt(new Date());

        return model;
    }

    private CatEditorialesEntity generateEditorial() {
        CatEditorialesEntity editorial = new CatEditorialesEntity();
        editorial.setIdEditorial(this.uuid);
        editorial.setDescripcion(this.description);
        editorial.setCreatedAt(new Date());
        editorial.setUpdatedAt(new Date());
        return editorial;
    }

    private CatFabricantesEntity generateFabricante() {
        CatFabricantesEntity fabricante = new CatFabricantesEntity();
        fabricante.setIdFabricante(this.uuid);
        fabricante.setDescripcion(this.description);
        fabricante.setCreatedAt(new Date());
        fabricante.setUpdatedAt(new Date());
        return fabricante;
    }

    private CatFranquiciasEntity generateFranquicia() {
        CatFranquiciasEntity franquicia = new CatFranquiciasEntity();
        franquicia.setIdFranquicia(this.uuid);
        franquicia.setDescripcion(this.description);
        franquicia.setCreatedAt(new Date());
        franquicia.setUpdatedAt(new Date());
        return franquicia;
    }

    private CatTiposEntity generateTipo() {
        CatTiposEntity tipo = new CatTiposEntity();
        tipo.setIdTipo(this.id);
        tipo.setDescripcion(this.description);
        tipo.setCreatedAt(new Date());
        tipo.setUpdatedAt(new Date());
        return tipo;
    }

}