package com.metaverse.files.ro.scene.response;


import com.metaverse.files.ro.responses.ResultDetailsRO;
import com.metaverse.files.ro.scene.SceneInfoRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Ответ на запрос получения информации о сцене.
 *
 * @author Mikhail.Kataranov
 * @since 12.05.2025
 */
@Schema(description = "Результат запроса информации о сцене")
public class SceneInfoResultRO extends ResultDetailsRO {

    @Schema(description = "Информация о сцене")
    private SceneInfoRO sceneInfo;

    /**
     * @return информация о сцене
     */
    public SceneInfoRO getSceneInfo() {
        return sceneInfo;
    }

    /**
     * @param sceneInfo информация о сцене
     */
    public void setSceneInfo(SceneInfoRO sceneInfo) {
        this.sceneInfo = sceneInfo;
    }
}
