package com.metaverse.files.ro.regkey;

import java.util.Date;

import com.metaverse.files.ro.role.RoleRO;
import com.metaverse.files.ro.role.SecurityRoleRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rest Object ключа регистрации.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Schema(description = "Объект ключа регистрации")
public class RegistrationKeyRO {

    @Schema(description = "Ключ регистрации")
    private String key;
    @Schema(description = "Дата начала периода актуальности ключа")
    private Date dateFrom;
    @Schema(description = "Дата окончания периода актуальности ключа")
    private Date dateTo;
    @Schema(description = "Роль на файловом сервере")
    private SecurityRoleRO securityRole;
    @Schema(description = "Роль в приложении")
    private RoleRO role;
    @Schema(description = "Организация создавшая ключ")
    private String organization;

    /**
     * @return ключ регистрации
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key ключ регистрации
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return дата начала периода актуальности ключа
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom дата начала периода актуальности ключа
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return дата окончания периода актуальности ключа
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo дата окончания периода актуальности ключа
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @return роль на файловом сервере
     */
    public SecurityRoleRO getSecurityRole() {
        return securityRole;
    }

    /**
     * @param securityRole роль на файловом сервере
     */
    public void setSecurityRole(SecurityRoleRO securityRole) {
        this.securityRole = securityRole;
    }

    /**
     * @return роль в приложении
     */
    public RoleRO getRole() {
        return role;
    }

    /**
     * @param role роль в приложении
     */
    public void setRole(RoleRO role) {
        this.role = role;
    }

    /**
     * @return организация создавшая ключ
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * @param organization организация создавшая ключ
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
