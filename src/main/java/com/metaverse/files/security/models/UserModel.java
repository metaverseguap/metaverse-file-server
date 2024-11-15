package com.metaverse.files.security.models;

import com.metaverse.files.models.HostModel;
import com.metaverse.files.models.RoleModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Hibernate модель представляющая объект User.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "hashed_password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_key_id", referencedColumnName = "id")
    private RegistrationKeyModel registrationKeyModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private SecurityRoleModel securityRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_role_id", referencedColumnName = "id")
    private RoleModel role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private HostModel hostModel;

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return имя пользователя
     */
    public String getName() {
        return name;
    }

    /**
     * @param name имя пользователя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return зашифрованный пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password зашифрованный пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return {@link RegistrationKeyModel ключ регистрации}, под которым зарегистрировался данный пользователь
     */
    public RegistrationKeyModel getRegistrationKey() {
        return registrationKeyModel;
    }

    /**
     * @param registrationKeyModel {@link RegistrationKeyModel ключ регистрации}, под которым зарегистрировался данный пользователь
     */
    public void setRegistrationKey(RegistrationKeyModel registrationKeyModel) {
        this.registrationKeyModel = registrationKeyModel;
    }

    /**
     * @return {@link SecurityRoleModel роль на файловом сервере}
     */
    public SecurityRoleModel getSecurityRole() {
        return securityRole;
    }

    /**
     * @param securityRoleModel {@link SecurityRoleModel роль на файловом сервере}
     */
    public void setSecurityRole(SecurityRoleModel securityRoleModel) {
        this.securityRole = securityRoleModel;
    }

    /**
     * @return хост, которым является пользователь
     */
    public HostModel getHostModel() {
        return hostModel;
    }

    /**
     * @param hostModels хост, которым является пользователь
     */
    public void setHostModel(HostModel hostModels) {
        this.hostModel = hostModels;
    }

    /**
     * @return {@link RoleModel роль} пользователя в приложении
     */
    public RoleModel getRole() {
        return role;
    }

    /**
     * @param role {@link RoleModel роль} пользователя в приложении
     */
    public void setRole(RoleModel role) {
        this.role = role;
    }
}
