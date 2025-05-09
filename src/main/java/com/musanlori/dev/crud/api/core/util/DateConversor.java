package com.musanlori.dev.crud.api.core.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public final class DateConversor {

    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

    /**
     * Convert a java.util.Date Object into a LocalDateTime String.
     * @param date date Object to convert
     * @return {@link String} datetime string in format "dd-MM-yyyy hh:mm:ss"
     * */
    public static String toLocalDateTimeFormat(final Date date) {
        log.info("Executing: toLocalDateTimeFormat");

        if (date == null) {
            return null;
        }

        try {
            LocalDateTime ldt = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
            return ldt.format(formatter);
        } catch (Exception e) {
            log.error("operation Failed. Can not convert the java.util.Date to LocalDateTime String format.");
            log.error("ERROR: {}", e.getMessage());
            log.info("Returning default value");
            return date.toString();
        }

    }

    private DateConversor() { }
}
