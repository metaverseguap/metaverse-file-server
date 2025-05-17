package com.metaverse.files.ro.scene.response;


import java.util.List;

import com.metaverse.files.ro.responses.ResultDetailsRO;
import com.metaverse.files.ro.scene.SceneUpdateInfoRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Ответ на запрос получения множества информаций о датах обновления файлов сцен.
 *
 * @author Mikhail.Kataranov
 * @since 17.05.2025
 */
@Schema(description = "Результат запроса множества информаций о датах обновления файлов сцен")
public class SceneUpdateInfosResultRO extends ResultDetailsRO {

    @Schema(description = "Список с информацией о датах обновления файлов сцен")
    private List<SceneUpdateInfoRO> updateInfos;

    /**
     * @return список с информацией о датах обновления файлов сцен
     */
    public List<SceneUpdateInfoRO> getUpdateInfos() {
        return updateInfos;
    }

    /**
     * @param updateInfos список с информацией о датах обновления файлов сцен
     */
    public void setUpdateInfos(List<SceneUpdateInfoRO> updateInfos) {
        this.updateInfos = updateInfos;
    }
}
