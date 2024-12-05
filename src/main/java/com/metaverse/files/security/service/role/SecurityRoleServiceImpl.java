package com.metaverse.files.security.service.role;


import java.util.EnumSet;
import java.util.List;

import com.metaverse.files.converters.role.SecurityRoleConverter;
import com.metaverse.files.ro.role.SecurityRoleRO;
import com.metaverse.files.security.types.UserServerRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса работы с ролями файлового сервера.
 *
 * @author Mikhail.Kataranov
 * @since 23.11.2024
 */
@Service
@Transactional(readOnly = true)
public class SecurityRoleServiceImpl implements SecurityRoleService {

    @Autowired
    private SecurityRoleConverter roleConverter;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SecurityRoleRO> getAll() {
        EnumSet<UserServerRole> userServerRoles = EnumSet.allOf(UserServerRole.class);

        return roleConverter.to(userServerRoles);
    }
}
