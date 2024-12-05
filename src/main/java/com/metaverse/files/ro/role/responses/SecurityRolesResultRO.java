package com.metaverse.files.ro.role.responses;


import java.util.List;

import com.metaverse.files.ro.response.ResultDetailsRO;
import com.metaverse.files.ro.role.SecurityRoleRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Ответ на запрос получения множества ролей сервера.
 *
 * @author Mikhail.Kataranov
 * @since 23.11.2024
 */
@Schema(description = "Результат запроса множества ролей файлового сервера")
public class SecurityRolesResultRO extends ResultDetailsRO {

    @Schema(description = "Роли файлового сервера")
    private List<SecurityRoleRO> roles;

    /**
     * @return {@link SecurityRoleRO роли файлового сервера}
     */
    public List<SecurityRoleRO> getRoles() {
        return roles;
    }

    /**
     * @param roles {@link SecurityRoleRO роли файлового сервера}
     */
    public void setRoles(List<SecurityRoleRO> roles) {
        this.roles = roles;
    }
}
