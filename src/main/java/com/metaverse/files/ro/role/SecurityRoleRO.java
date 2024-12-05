package com.metaverse.files.ro.role;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rest Object роли пользователя на файловом сервере.
 *
 * @author Mikhail.Kataranov
 * @since 23.11.2024
 */
@Schema(description = "Объект роли пользователя на файловом сервере")
public class SecurityRoleRO {

    @Schema(description = "Название роли")
    private String name;

    /**
     * @return название роли
     */
    public String getName() {
        return name;
    }

    /**
     * @param name название роли
     */
    public void setName(String name) {
        this.name = name;
    }
}
