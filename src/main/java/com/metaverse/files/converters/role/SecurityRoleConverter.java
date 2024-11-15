package com.metaverse.files.converters.role;


import com.metaverse.files.converters.Converter;
import com.metaverse.files.ro.role.SecurityRoleRO;
import com.metaverse.files.security.types.UserServerRole;
import org.springframework.stereotype.Component;

/**
 * Конвертер UserServerRole в SecurityRoleRO.
 *
 * @author Mikhail.Kataranov
 * @since 23.11.2024
 */
@Component
public class SecurityRoleConverter extends Converter<UserServerRole, SecurityRoleRO> {

    /**
     * Конструктор.
     */
    public SecurityRoleConverter() {
        super();
        this.from = null;
        this.to = this::convert;
    }

    private SecurityRoleRO convert(final UserServerRole role) {
        SecurityRoleRO ro = new SecurityRoleRO();
        ro.setName(role.name());

        return ro;
    }
}
