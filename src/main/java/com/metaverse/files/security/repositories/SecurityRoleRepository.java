package com.metaverse.files.security.repositories;

import java.util.Optional;

import com.metaverse.files.security.models.SecurityRoleModel;
import com.metaverse.files.security.types.UserServerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA репозиторий для упрощенных запросов к БД ролей файлового сервера.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Repository
public interface SecurityRoleRepository extends JpaRepository<SecurityRoleModel, Integer> {

    /**
     * Найти {@link SecurityRoleModel роль} по id.
     *
     * @param id id
     * @return {@link SecurityRoleModel роль на файловом сервере}
     */
    Optional<SecurityRoleModel> findById(int id);

    /**
     * Найти {@link SecurityRoleModel роль} по имени.
     *
     * @param role имя роли
     * @return {@link SecurityRoleModel роль на файловом сервере}
     */
    Optional<SecurityRoleModel> findByRole(UserServerRole role);
}
