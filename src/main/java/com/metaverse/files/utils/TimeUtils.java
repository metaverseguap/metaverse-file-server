package com.metaverse.files.utils;


import java.time.Instant;
import java.util.Date;

/**
 * Часто используемые методы в работе с датой и временем.
 *
 * @author Mikhail.Kataranov
 * @since 12.05.2025
 */
public class TimeUtils {

    private TimeUtils() {}

    /**
     * Получить текущую дату и время в формате Date.
     *
     * @return текущая дата и время в формате Date
     */
    public static Date dateNow() {
        return Date.from(Instant.now());
    }

    /**
     * Получить текущую дату и время в формате количества миллисекунд, прошедших с 00:00:00 UTC 1 января 1970 года.
     *
     * @return текущая дата и время в формате количества миллисекунд, прошедших с 00:00:00 UTC 1 января 1970 года
     */
    public static long millisNow() {
        return Instant.now().toEpochMilli();
    }
}
