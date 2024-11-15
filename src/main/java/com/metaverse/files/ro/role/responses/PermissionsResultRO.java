package com.metaverse.files.ro.role.responses;


import java.util.List;

import com.metaverse.files.ro.response.ResultDetailsRO;
import com.metaverse.files.ro.role.PermissionRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Ответ на запрос получения информации о множестве прав (разрешений).
 *
 * @author Mikhail.Kataranov
 * @since 24.11.2024
 */
@Schema(description = "Результат получения информации о множестве прав (разрешений)")
public class PermissionsResultRO extends ResultDetailsRO {

    @Schema(description = "Права (разрешения)")
    private List<PermissionRO> permissions;

    /**
     * @return права (разрешения)
     */
    public List<PermissionRO> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions права (разрешения)
     */
    public void setPermissions(List<PermissionRO> permissions) {
        this.permissions = permissions;
    }
}
