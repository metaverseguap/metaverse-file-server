package com.metaverse.files.services.user;


import java.util.Set;

/**
 * Сервис статуса пользователей.
 *
 * @author Mikhail.Kataranov
 * @since 12.05.2025
 */
public interface UserStatusService {

    /**
     * Получить количество секунд, после прохождения которых статус перестанет считаться валидным.
     *
     * @return количество секунд, после прохождения которых статус перестанет считаться валидным
     */
    Integer getSecondsOfStatusExpiration();

    /**
     * Обновить статус пользователя, вызвавшего данный метод.
     */
    void updateUserStatus();

    /**
     * Обновить статус пользователя с указанным логином.
     *
     * @param login логин пользователя
     */
    void updateUserStatus(String login);

    /**
     * Получить набор логинов пользователй, активных в данный момент.
     *
     * @return набор логинов пользователй, активных в данный момент
     */
    Set<String> activeUsersLogins();
}
