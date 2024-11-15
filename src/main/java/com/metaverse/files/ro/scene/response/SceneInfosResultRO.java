package com.metaverse.files.ro.scene.response;


import java.util.List;

import com.metaverse.files.ro.response.ResultDetailsRO;
import com.metaverse.files.ro.scene.SceneInfoRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Ответ на запрос получения множества информаций о сценах.
 *
 * @author Mikhail.Kataranov
 * @since 25.11.2024
 */
@Schema(description = "Результат запроса множества информаций о сценах")
public class SceneInfosResultRO extends ResultDetailsRO {

    @Schema(description = "Список с информацией о сценах")
    private List<SceneInfoRO> infoList;

    /**
     * @return список с информацией о сценах
     */
    public List<SceneInfoRO> getInfoList() {
        return infoList;
    }

    /**
     * @param infoList список с информацией о сценах
     */
    public void setInfoList(List<SceneInfoRO> infoList) {
        this.infoList = infoList;
    }
}
