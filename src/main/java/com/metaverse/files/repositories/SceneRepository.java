package com.metaverse.files.repositories;

import java.util.List;
import java.util.Optional;

import com.metaverse.files.models.SceneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA репозиторий для упрощенных запросов к БД сцен.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Repository
public interface SceneRepository extends JpaRepository<SceneModel, Integer> {

    /**
     * Получить {@link SceneModel модель сцены} по имени.
     *
     * @param name имя сцены
     * @return {@link SceneModel модель сцены}
     */
    Optional<SceneModel> findByName(String name);

    /**
     * Получить множество {@link SceneModel моделей сцен} по именам.
     *
     * @param sceneNames имена аватаров
     * @return множество {@link SceneModel моделей сцен} имеющих указанные имена
     */
    List<SceneModel> findAllByNameIn(List<String> sceneNames);

    /**
     * Удалить сцены имеющие указанные имена.
     *
     * @param sceneNames имена удаляемых сцен
     */
    void deleteAllByNameIn(List<String> sceneNames);
}
