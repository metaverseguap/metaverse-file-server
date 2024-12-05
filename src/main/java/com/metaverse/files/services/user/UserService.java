package com.metaverse.files.services.user;

import com.metaverse.files.ro.user.UserRO;
import com.metaverse.files.utils.exceptions.DataNotFoundException;

/**
 * Сервис пользователей.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
public interface UserService {

    /**
     * Получить имя пользователя по логину пользователя.
     *
     * @param login логин пользователя
     * @return имя пользователя
     * @throws DataNotFoundException если пользователь с таким логином не был найден в БД
     */
    String getNameByLogin(String login);

    /**
     * Получить информация о пользователе по логину пользователя.
     *
     * @param login логин пользователя
     * @return информация о пользователе
     * @throws DataNotFoundException если пользователь с таким логином не был найден в БД
     */
    UserRO getInfoByLogin(String login);

    /**
     * Получить информацию об авторизованном в данный момент пользователе.
     *
     * @return информация о пользователе
     */
    UserRO getCurrentUser();
}
