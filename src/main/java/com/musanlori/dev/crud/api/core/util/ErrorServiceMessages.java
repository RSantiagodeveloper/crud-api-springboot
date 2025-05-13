package com.musanlori.dev.crud.api.core.util;

public class ErrorServiceMessages {

    public static final String OPERATION_SUCCESS_CODE = "0000";
    public static final String REQUEST_FIELD_CODE = "0001";
    public static final String INVALID_FORMAT_CODE = "0002";
    public static final String RESOURCE_NOT_FOUND_CODE = "0004";
    public static final String OPERATION_CRUD_FAILED_CODE = "0008";
    public static final String GRAL_ERROR_CODE = "0009";

    public static final String OPERATION_SUCCESS_MSG = "Operation Finished Success";
    public static final String REQUEST_FIELD_MSG = "The field is Required";
    public static final String INVALID_FORMAT_MSG = "Invalid Format in the field '%S'";
    public static final String RESOURCE_NOT_FOUND_MSG = "The resource requesting NOT FOUND";
    public static final String INVALID_ID_MSG = "ERROR: Should especific a valid id for '%S'";
    public static final String INVALID_DESCRIPTION_MSG = "ERROR: Should especific a valid name for '%S'";
    public static final String INVALID_TIPO_MSG = "ERROR: Should especific a valid type for '%S'";
    public static final String CREATE_OPERATION_ERROR_MSG = "Fail to create the entity. Error %s";
    public static final String UPDATE_OPERATION_ERROR_MSG = "Fail to update the %s entity";
    public static final String DELETE_OPERATION_ERROR_MSG = "Fail to delete the element whit id %s";
    public static final String GRAL_ERROR_MSG = "The service has experienced an internal issue. Process unfinished";

    public static final String SERVICE_EXCEPTION_LOG_MESSAGE = "<==== OPERATION FAILED | INTERCEPTED {} ====>";

}
