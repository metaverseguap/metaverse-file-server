package com.metaverse.files.security.service.role;

import java.util.List;

import com.metaverse.files.ro.role.SecurityRoleRO;

/**
 * Сервис работы с ролями файлового сервера.
 *
 * @author Mikhail.Kataranov
 * @since 23.11.2024
 */
public interface SecurityRoleService {

    /**
     * Получает все роли файлового сервера.
     *
     * @return все роли файлового сервера
     */
    List<SecurityRoleRO> getAll();
}
