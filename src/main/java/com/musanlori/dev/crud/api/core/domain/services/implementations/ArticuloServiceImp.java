package com.musanlori.dev.crud.api.core.domain.services.implementations;

import com.musanlori.dev.crud.api.core.application.models.request.ArticuloRequestModel;
import com.musanlori.dev.crud.api.core.application.models.response.ArticuloResponse;
import com.musanlori.dev.crud.api.core.application.models.response.GeneralResponseService;
import com.musanlori.dev.crud.api.core.domain.model.entity.ArticulosEntity;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatEditorialesEntity;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatFabricantesEntity;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatFranquiciasEntity;
import com.musanlori.dev.crud.api.core.domain.model.entity.CatTiposEntity;
import com.musanlori.dev.crud.api.core.domain.services.definitions.IArticuloService;
import com.musanlori.dev.crud.api.core.errors.exceptions.LogicServiceConflictException;
import com.musanlori.dev.crud.api.core.errors.exceptions.NotFoundResourceException;
import com.musanlori.dev.crud.api.core.errors.exceptions.RequestDataNotValidException;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaArticulosRepository;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatEditorialesRepository;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatFabricanteRepository;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatFranquiciasEntity;
import com.musanlori.dev.crud.api.core.infrastructure.repository.JpaCatTiposRepository;
import com.musanlori.dev.crud.api.core.util.DateConversor;
import com.musanlori.dev.crud.api.core.util.ErrorServiceMessages;
import com.musanlori.dev.crud.api.core.util.ObjectsDebuggerLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArticuloServiceImp implements IArticuloService {

    @Autowired
    private JpaArticulosRepository jpaArticulosRepository;

    @Autowired
    private JpaCatEditorialesRepository jpaCatEditorialesRepository;

    @Autowired
    private JpaCatFabricanteRepository jpaCatFabricanteRepository;

    @Autowired
    private JpaCatFranquiciasEntity jpaCatFranquiciasEntity;

    @Autowired
    private JpaCatTiposRepository jpaCatTiposRepository;

    private final String entityName = "Articulo";

    /**
     * {@inheritDoc}
     * */
    @Override
    public GeneralResponseService addNewArticulo(final ArticuloRequestModel request) {
        log.info("EXECUTING SERVICE ADD_NEW_ARTICULO");

        ArticulosEntity entity =  new ArticulosEntity();
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setPrecio(request.getPrecio());
        entity.setUnidades(request.getUnidades());
        entity.setCalificacion(request.getCalificacion());

        if (request.getTipo() != null) {
            try {
                entity.setTipo(findTipoEntity(request.getTipo().getIdElement()));
            } catch (Exception e) {
                log.error("{}", e.getMessage());
                throw new LogicServiceConflictException(
                        ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                        ErrorServiceMessages.CREATE_OPERATION_ERROR_MSG,
                        e.getMessage()
                );
            }
        }

        if (request.getFabricante() != null) {
            try {
                entity.setFabricante(findFabricanteEntity(request.getFabricante().getUuidElement()));
            } catch (Exception e) {
                log.error("{}", e.getMessage());
                throw new LogicServiceConflictException(
                        ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                        ErrorServiceMessages.CREATE_OPERATION_ERROR_MSG,
                        e.getMessage()
                );
            }
        }

        if (request.getEditorial() != null) {
            try {
                entity.setEditorial(findEditorialEntity(request.getEditorial().getUuidElement()));
            } catch (Exception e) {
                log.error("{}", e.getMessage());
                throw new LogicServiceConflictException(
                        ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                        ErrorServiceMessages.CREATE_OPERATION_ERROR_MSG,
                        e.getMessage()
                );
            }
        }

        if (request.getFranquicia() != null) {
            try {
                entity.setFranquicia(findFranquiciaEntity(request.getFranquicia().getUuidElement()));
            } catch (Exception e) {
                log.error("{}", e.getMessage());
                throw new LogicServiceConflictException(
                        ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                        ErrorServiceMessages.CREATE_OPERATION_ERROR_MSG,
                        e.getMessage()
                );
            }
        }

        entity.setCreatedAt(new Date());

        ObjectsDebuggerLog.showInJsonFormat(entity);

        try {
            jpaArticulosRepository.save(entity);
            return new GeneralResponseService(ErrorServiceMessages.OPERATION_SUCCESS_CODE,
                    ErrorServiceMessages.OPERATION_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.toString());
            log.error("Save Operation Failed. Error: {}", e.getMessage());
            return new GeneralResponseService(
                    ErrorServiceMessages.GRAL_ERROR_CODE,
                    ErrorServiceMessages.GRAL_ERROR_MSG
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ArticuloResponse> getAllArticulos() {
        log.info("EXECUTING SERVICE GET_ALL_EDITORIALES");

        List<ArticulosEntity> results = jpaArticulosRepository.findAll();
        List<ArticuloResponse> articulos = new ArrayList<>();

        for (ArticulosEntity entity: results) {
            articulos.add(setArticulosResponse(entity));
        }
        return articulos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticuloResponse getArticulo(final String uuid) {
        log.info("EXECUTING SERVICE GET_EDITORIAL");

        if (uuid == null || uuid.isEmpty()) {
            log.error("invalid uuid. read operation stopped");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG, entityName);
        }

        Optional<ArticulosEntity> result = jpaArticulosRepository.findById(uuid);

        if (result.isEmpty()) {
            log.error("The entity has not found");
            throw new NotFoundResourceException(ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE,
                    ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG);
        }


        return setArticulosResponse(result.get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticuloResponse updateArticulo(final ArticuloRequestModel request) {
        log.info("EXECUTING SERVICE UPDATE_ARTICULO");

        if (request.getUuid() == null || request.getUuid().isEmpty()) {
            log.error("invalid uuid. update operation stopped");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_ID_MSG, entityName);
        }

        Optional<ArticulosEntity> result = jpaArticulosRepository.findById(request.getUuid());

        ArticulosEntity entity = result.orElseThrow(() -> new NotFoundResourceException(
                ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE,
                ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG
        ));

        entity.setNombre((request.getNombre() != null) ? request.getNombre() : entity.getNombre());
        entity.setDescripcion((request.getDescripcion() != null) ? request.getDescripcion() : entity.getDescripcion());
        entity.setPrecio((request.getPrecio() != null) ? request.getPrecio() : entity.getPrecio());
        entity.setUnidades((request.getUnidades() != null) ? request.getUnidades() : entity.getUnidades());
        entity.setCalificacion((request.getCalificacion() != null) ? request.getCalificacion() : entity.getCalificacion());

        if (request.getTipo() != null) {
            try {
                entity.setTipo(findTipoEntity(request.getTipo().getIdElement()));
            } catch (Exception e) {
                log.error("{}", e.getMessage());
                throw new LogicServiceConflictException(
                        ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                        ErrorServiceMessages.CREATE_OPERATION_ERROR_MSG,
                        e.getMessage()
                );
            }
        } else {
            entity.setTipo(entity.getTipo());
        }

        if (request.getFabricante() != null) {
            try {
                entity.setFabricante(findFabricanteEntity(request.getFabricante().getUuidElement()));
            } catch (Exception e) {
                log.error("{}", e.getMessage());
                throw new LogicServiceConflictException(
                        ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                        ErrorServiceMessages.CREATE_OPERATION_ERROR_MSG,
                        e.getMessage()
                );
            }
        } else {
            entity.setFabricante(entity.getFabricante());
        }

        if (request.getEditorial() != null) {
            try {
                entity.setEditorial(findEditorialEntity(request.getEditorial().getUuidElement()));
            } catch (Exception e) {
                log.error("{}", e.getMessage());
                throw new LogicServiceConflictException(
                        ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                        ErrorServiceMessages.CREATE_OPERATION_ERROR_MSG,
                        e.getMessage()
                );
            }
        } else {
            entity.setEditorial(entity.getEditorial());
        }

        if (request.getFranquicia() != null) {
            try {
                entity.setFranquicia(findFranquiciaEntity(request.getFranquicia().getUuidElement()));
            } catch (Exception e) {
                log.error("{}", e.getMessage());
                throw new LogicServiceConflictException(
                        ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                        ErrorServiceMessages.CREATE_OPERATION_ERROR_MSG,
                        e.getMessage()
                );
            }
        } else {
            entity.setFranquicia(entity.getFranquicia());
        }

        entity.setCreatedAt(entity.getCreatedAt());
        entity.setUpdatedAt(new Date());

        try {
            ArticulosEntity savedEntity = jpaArticulosRepository.save(entity);
            return setArticulosResponse(savedEntity);
        } catch (Exception e) {
            log.error("Update Operation Failed. Error: {}", e.getMessage());
            throw new LogicServiceConflictException(
                    ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                    ErrorServiceMessages.UPDATE_OPERATION_ERROR_MSG,
                    entityName
            );
        }
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public GeneralResponseService deleteArticulo(final String uuid) {
        log.info("EXECUTING SERVICE DELETE_EDITORIAL");

        if (uuid == null || uuid.isEmpty()) {
            log.error("invalid uuid. delete operation stopped");
            throw new RequestDataNotValidException(ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_DESCRIPTION_MSG,
                    entityName);
        }

        Optional<ArticulosEntity> queryResult = jpaArticulosRepository.findById(uuid);
        queryResult.orElseThrow(() -> new NotFoundResourceException(
                ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE, ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG));

        try {
            jpaArticulosRepository.deleteById(uuid);
            return new GeneralResponseService(ErrorServiceMessages.OPERATION_SUCCESS_CODE,
                    ErrorServiceMessages.OPERATION_SUCCESS_MSG);
        } catch (Exception e) {
            throw new LogicServiceConflictException(ErrorServiceMessages.OPERATION_CRUD_FAILED_CODE,
                    ErrorServiceMessages.DELETE_OPERATION_ERROR_MSG,
                    entityName);
        }
    }

    private CatEditorialesEntity findEditorialEntity(final String uuid) throws Exception {
        log.info("Executing: find_Editorial_Entity");

        if (uuid == null || uuid.isEmpty()) {
            throw new RequestDataNotValidException(
                    ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_ID_MSG,
                    "Editorial"
            );
        }

        Optional<CatEditorialesEntity> element = jpaCatEditorialesRepository.findById(uuid);
        return element.orElseThrow(() -> new NotFoundResourceException(
                ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE,
                ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG
        ));
    }

    private CatFabricantesEntity findFabricanteEntity(final String uuid) throws Exception {
        log.info("Executing: find_Fabricante_Entity");

        if (uuid == null || uuid.isEmpty()) {
            throw new RequestDataNotValidException(
                    ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_ID_MSG,
                    "Fabricante"
            );
        }

        Optional<CatFabricantesEntity> element = jpaCatFabricanteRepository.findById(uuid);
        return element.orElseThrow(() -> new NotFoundResourceException(
                ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE,
                ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG
        ));
    }

    private CatFranquiciasEntity findFranquiciaEntity(final String uuid) throws Exception {
        log.info("Executing: find_Franquicia_Entity");

        if (uuid == null || uuid.isEmpty()) {
            throw new RequestDataNotValidException(
                    ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_ID_MSG,
                    "Franquicia"
            );
        }

        Optional<CatFranquiciasEntity> element = jpaCatFranquiciasEntity.findById(uuid);
        return element.orElseThrow(() -> new NotFoundResourceException(
                ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE,
                ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG
        ));
    }

    private CatTiposEntity findTipoEntity(final Integer id) throws Exception {
        log.info("Executing: find_Tipo_Entity");
        if (id == null) {
            throw new RequestDataNotValidException(
                    ErrorServiceMessages.REQUEST_FIELD_CODE,
                    ErrorServiceMessages.INVALID_ID_MSG,
                    "Tipo"
            );
        }

        Optional<CatTiposEntity> element = jpaCatTiposRepository.findById(id);
        return element.orElseThrow(() -> new NotFoundResourceException(
                ErrorServiceMessages.RESOURCE_NOT_FOUND_CODE,
                ErrorServiceMessages.RESOURCE_NOT_FOUND_MSG
        ));
    }

    private ArticuloResponse setArticulosResponse(final ArticulosEntity entity) {
        log.info("Executing: set_Articulos_Response");

        ArticuloResponse response = new ArticuloResponse();

        response.setUuid(entity.getIdArticulo());
        response.setNombre(entity.getNombre());
        response.setDescripcion(entity.getDescripcion());
        response.setPrecio(entity.getPrecio());
        response.setUnidades(entity.getUnidades());
        response.setCalificacion(entity.getCalificacion());
        response.setTipo((entity.getTipo() != null) ? entity.getTipo().getDescripcion() : null);
        response.setFranquicia((entity.getFranquicia() != null) ? entity.getFranquicia().getDescripcion() : null);
        response.setFabricante((entity.getFabricante() != null) ? entity.getFabricante().getDescripcion() : null);
        response.setEditorial((entity.getEditorial() != null) ? entity.getEditorial().getDescripcion() : null);
        response.setCreated(DateConversor.toLocalDateTimeFormat(entity.getCreatedAt()));
        response.setUpdate(DateConversor.toLocalDateTimeFormat(entity.getUpdatedAt()));

        return response;
    }
}
