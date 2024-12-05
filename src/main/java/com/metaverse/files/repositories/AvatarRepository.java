package com.metaverse.files.repositories;


import java.util.List;
import java.util.Optional;

import com.metaverse.files.models.AvatarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA репозиторий для упрощенных запросов к БД аватаров.
 *
 * @author Mikhail.Kataranov
 * @since 28.11.2024
 */
@Repository
public interface AvatarRepository extends JpaRepository<AvatarModel, Integer> {

    /**
     * Получить {@link AvatarModel модель аватара} по имени.
     *
     * @param name имя аватара
     * @return {@link AvatarModel модель аватара}
     */
    Optional<AvatarModel> findByName(String name);

    /**
     * Удалить аватары имеющие указанные имена.
     *
     * @param avatarNames имена удаляемых аватаров
     */
    void deleteAllByNameIn(List<String> avatarNames);

    /**
     * Получить множество {@link AvatarModel моделей аватара} по именам.
     *
     * @param avatarNames имена аватаров
     * @return множество {@link AvatarModel моделей аватара} имеющих указанные имена
     */
    List<AvatarModel> findAllByNameIn(List<String> avatarNames);
}
