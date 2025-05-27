package com.musanlori.dev.crud.api.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ObjectsDebuggerLog {

    /**
     * shows in a JSON string format an object that require debug.
     * @param object object to map
     * */
    public static void showInJsonFormat(final Object object) {
        try {
            log.debug("debugging {} object", object.getClass());
            log.debug(new ObjectMapper().writer().writeValueAsString(object));
        } catch (JsonProcessingException e) {
            log.error("{}", e.getMessage());
            log.error("fail to map the {} object", object.getClass().getName());
        }
    }

    private ObjectsDebuggerLog() { }

}
