package com.metaverse.files.ro.role.requests;


import java.util.List;

import com.metaverse.files.ro.role.PermissionRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rest Object запроса создания множества прав (разрешений).
 *
 * @author Mikhail.Kataranov
 * @since 24.11.2024
 */
@Schema(description = "Запрос создания множества прав (разрешений)")
public class CreatePermissionsRequestRO {

    @Schema(description = "Список создаваемых прав (разрешений)")
    private List<PermissionRO> permissions;

    /**
     * @return список создаваемых прав (разрешений)
     */
    public List<PermissionRO> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions список создаваемых прав (разрешений)
     */
    public void setPermissions(List<PermissionRO> permissions) {
        this.permissions = permissions;
    }
}
