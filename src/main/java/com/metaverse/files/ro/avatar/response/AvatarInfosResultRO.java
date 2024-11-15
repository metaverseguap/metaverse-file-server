package com.metaverse.files.ro.avatar.response;


import java.util.List;

import com.metaverse.files.ro.avatar.AvatarInfoRO;
import com.metaverse.files.ro.response.ResultDetailsRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Ответ на запрос получения множества информаций об аватарах.
 *
 * @author Mikhail.Kataranov
 * @since 28.11.2024
 */
@Schema(description = "Ответ на запрос получения множества информаций об аватарах")
public class AvatarInfosResultRO extends ResultDetailsRO {

    @Schema(description = "Список с информацией об аватарах")
    private List<AvatarInfoRO> infoList;

    /**
     * @return список с информацией об аватарах
     */
    public List<AvatarInfoRO> getInfoList() {
        return infoList;
    }

    /**
     * @param infoList список с информацией об аватарах
     */
    public void setInfoList(List<AvatarInfoRO> infoList) {
        this.infoList = infoList;
    }
}
