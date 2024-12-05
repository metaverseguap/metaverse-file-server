package com.metaverse.files.ro.role.responses;


import com.metaverse.files.ro.response.ResultDetailsRO;
import com.metaverse.files.ro.role.RoleRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Ответ на запрос получения информации о роли.
 *
 * @author Mikhail.Kataranov
 * @since 16.11.2024
 */
@Schema(description = "Результат получения информации о роли")
public class RoleInfoResultRO extends ResultDetailsRO {

    @Schema(description = "Информация о роли")
    private RoleRO roleInfo;

    /**
     * @return {@link RoleRO роль}
     */
    public RoleRO getRoleInfo() {
        return roleInfo;
    }

    /**
     * @param roleInfo {@link RoleRO роль}
     */
    public void setRoleInfo(RoleRO roleInfo) {
        this.roleInfo = roleInfo;
    }
}
