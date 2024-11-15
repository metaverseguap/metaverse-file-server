package com.metaverse.files.ro.role.requests;


import java.util.List;

import com.metaverse.files.ro.role.RoleRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rest Object запроса создания множества ролей в приложении.
 *
 * @author Mikhail.Kataranov
 * @since 24.11.2024
 */
@Schema(description = "Запрос создания множества ролей")
public class CreateRolesRequestRO {

    @Schema(description = "Список создаваемых ролей")
    private List<RoleRO> roles;

    /**
     * @return список создаваемых ролей
     */
    public List<RoleRO> getRoles() {
        return roles;
    }

    /**
     * @param roles список создаваемых ролей
     */
    public void setRoles(List<RoleRO> roles) {
        this.roles = roles;
    }
}
