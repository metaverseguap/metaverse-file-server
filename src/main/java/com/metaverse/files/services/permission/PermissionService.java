package com.metaverse.files.services.permission;

import java.util.List;

import com.metaverse.files.ro.role.PermissionRO;

/**
 * Сервис прав (разрешений).
 *
 * @author Mikhail.Kataranov
 * @since 24.11.2024
 */
public interface PermissionService {

    /**
     * Получить права (разрешения).
     *
     * @return список всех прав (разрешений)
     */
    List<PermissionRO> getAll();

    /**
     * Создать или обновить множество прав (разрешений).
     *
     * @param permissions права (разрешения)
     */
    void update(List<PermissionRO> permissions);

    /**
     * Удалить права (разрешения).
     *
     * @param permissions имена удаляемых прав (разрешений)
     */
    void delete(List<String> permissions);
}
