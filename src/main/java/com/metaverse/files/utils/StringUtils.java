package com.metaverse.files.utils;

/**
 * Часто используемые методы в работе со строками.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
public class StringUtils {

    private StringUtils() {}

    /**
     * Проверяет, является ли строка пустой.
     *
     * @param str строка
     * @return true, если строка пуста
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Проверяет равенство строк.
     *
     * @param str1 строка 1
     * @param str2 строка 2
     * @return true, если строки равны и не равны null
     */
    public static boolean stringEquals(String str1, String str2) {
        return str1 != null && str1.equals(str2);
    }
}
