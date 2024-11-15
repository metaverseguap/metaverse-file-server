package com.metaverse.files.services.role;

import java.util.List;

import com.metaverse.files.ro.role.RoleRO;
import com.metaverse.files.utils.exceptions.UselessOperationException;

/**
 * Сервис ролей.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
public interface RoleService {

    /**
     * Получить роли.
     *
     * @return список всех ролей
     */
    List<RoleRO> getAll();

    /**
     * Получить роль пользователя авторизованного в данный момент.
     *
     * @return роль пользователя авторизованного в данный момент
     */
    RoleRO getCurrentUserRole();

    /**
     * Создать или обновить роль.
     *
     * @param roleRO роль
     */
    void update(RoleRO roleRO);

    /**
     * Создать или обновить множество ролей.
     *
     * @param roles роли
     */
    void update(List<RoleRO> roles);

    /**
     * Удалить роль.
     *
     * @param id id удаляемой роли
     * @throws UselessOperationException если удаляемой роли нет в базе данных
     */
    void delete(int id);

    /**
     * Удалить роли.
     *
     * @param roleNames имена удаляемых ролей
     */
    void delete(List<String> roleNames);
}
