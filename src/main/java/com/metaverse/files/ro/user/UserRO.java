package com.metaverse.files.ro.user;

import com.metaverse.files.ro.role.RoleRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rest Object пользователя.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Schema(description = "Объект пользователя")
public class UserRO {

    @Schema(description = "Login пользователя")
    private String login;
    @Schema(description = "Отображаемое имя пользователя")
    private String name;
    @Schema(description = "Роль пользователя")
    private RoleRO role;

    /**
     * @return login пользователя
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login login пользователя
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return отображаемое имя пользователя
     */
    public String getName() {
        return name;
    }

    /**
     * @param name отображаемое имя пользователя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return роль пользователя
     */
    public RoleRO getRole() {
        return role;
    }

    /**
     * @param role роль пользователя
     */
    public void setRole(RoleRO role) {
        this.role = role;
    }
}
