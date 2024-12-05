package com.metaverse.files.services.scene;

import java.util.List;

import com.metaverse.files.contexts.scene.UploadSceneContext;
import com.metaverse.files.ro.scene.SceneFilePathRO;
import com.metaverse.files.ro.scene.SceneInfoRO;
import com.metaverse.files.utils.exceptions.DataNotFoundException;
import com.metaverse.files.utils.exceptions.InvalidRequestStateException;
import com.metaverse.files.utils.exceptions.ServerFileException;
import com.metaverse.files.utils.exceptions.UselessOperationException;

/**
 * Сервис сцен.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
public interface SceneService {

    /**
     * Получить список с информацией о сценах.
     *
     * @return список с информацией о сценах
     */
    List<SceneInfoRO> getAllInfo();

    /**
     * Получить сцену по имени.
     *
     * @param name имя сцены
     * @return путь до файла сцены
     * @throws DataNotFoundException если сцена с таким именем не была найдена в БД
     */
    SceneFilePathRO getSceneByName(String name);

    /**
     * Сохранить сцену.
     *
     * @param ctx {@link UploadSceneContext контекст сохранения сцены}
     * @throws InvalidRequestStateException если {@link UploadSceneContext контекст сохранения сцены} содержит невалидные данные
     * @throws ServerFileException          если при работе с файлами на диске произошли ошибки
     */
    void saveScene(UploadSceneContext ctx);

    /**
     * Удалить сцену по id.
     *
     * @param id id сцены
     * @throws UselessOperationException если удаляемой сцены нет в базе данных
     */
    void delete(int id);

    /**
     * Удалить сцены.
     *
     * @param sceneNames имена удаляемых сцен
     */
    void delete(List<String> sceneNames);
}
