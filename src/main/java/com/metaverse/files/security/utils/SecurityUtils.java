package com.metaverse.files.security.utils;


import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Часто используемые методы в работе с Spring Security.
 *
 * @author Mikhail.Kataranov
 * @since 12.05.2025
 */
public class SecurityUtils {

    private SecurityUtils() {}

    /**
     * Получить логин пользователя, авторизованного в данный момент.
     *
     * @return логин пользователя авторизованного, в данный момент
     * или null, если ни один пользователь не авторизован в данный момент
     */
    @Nullable
    public static String getAuthenticatedUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        }

        return null;
    }
}
