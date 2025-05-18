package com.metaverse.files.services.auth;

import com.metaverse.files.contexts.auth.LoginContext;
import com.metaverse.files.contexts.auth.RegistrationContext;
import com.metaverse.files.utils.exceptions.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Сервис авторизации.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
public interface AuthService {

    /**
     * Авторизовать пользователя.
     *
     * @param ctx {@link LoginContext контекст авторизации}
     * @param response HttpServletResponse, в cookies которого будет установлен refresh token
     * @return JSON Web Token
     * @throws AuthException если не удалось авторизовать пользователя
     */
    String login(LoginContext ctx, HttpServletResponse response);

    /**
     * Зарегистрировать пользователя.
     *
     * @param ctx {@link RegistrationContext контекст регистрации}
     * @param response HttpServletResponse, в cookies которого будет установлен refresh token
     * @return JSON Web Token
     * @throws AuthException если не удалось зарегистрировать пользователя
     */
    String registration(RegistrationContext ctx, HttpServletResponse response);

    /**
     * Обновить токен указанного запроса.
     *
     * @param request HttpServletRequest, в cookies которого содержится refresh JWT
     * @param response HttpServletResponse, в cookies которого будет установлен refresh token
     * @return обновленный JSON Web Token
     * @throws AuthException если не удалось обновить токен
     */
    String refresh(HttpServletRequest request, HttpServletResponse response);
}
