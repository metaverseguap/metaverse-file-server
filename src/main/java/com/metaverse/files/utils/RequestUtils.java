package com.metaverse.files.utils;


import jakarta.servlet.http.HttpServletRequest;

/**
 * Часто используемые методы в работе с запросами на сервер.
 *
 * @author Mikhail.Kataranov
 * @since 23.02.2025
 */
public class RequestUtils {

    private static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String HEADER_X_REAL_IP = "X-Real-IP";

    private static final String UNKNOWN = "unknown";

    private RequestUtils() {

    }

    /**
     * Получить ip клиента, осуществившего запрос на endpoint.
     *
     * @param request {@link HttpServletRequest}
     * @return ip клиента, осуществившего запрос на endpoint
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader(HEADER_X_FORWARDED_FOR);
        if (ipExists(ip)) {
            return ip;
        }

        ip = request.getHeader(HEADER_X_REAL_IP);
        if (ipExists(ip)) {
            return ip;
        }

        return request.getRemoteAddr();
    }

    private static boolean ipExists(String ip) {
        return ip != null && !ip.isEmpty()
                && !UNKNOWN.equalsIgnoreCase(ip);
    }
}
