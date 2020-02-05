package com.scalefocus.cvmanager.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Utility class that helps with the conversion of date formats.
 *
 * @author Mariyan Topalov
 */
public final class DateUtils {

    /**
     * Returns the argument {@link LocalDate} converted to {@link Date}.
     *
     * @param localDate the localDate to be converted
     * @return the argument converted to {@link Date}
     */
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Returns the argument {@link LocalDateTime} converted to {@link Date}.
     *
     * @param localDateTime the localDateTime to be converted
     * @return the argument converted to {@link Date}
     */
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Returns the argument {@link Date} converted to {@link LocalDate}.
     *
     * @param date the local to be converted
     * @return the argument converted to {@link LocalDate}
     */
    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Returns the argument {@link Date} converted to {@link LocalDateTime}.
     *
     * @param date the date to be converted
     * @return the argument converted to {@link LocalDateTime}
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * The class can't be instantiated.
     */
    private DateUtils() {
        throw new AssertionError();
    }
}
