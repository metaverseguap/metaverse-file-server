package com.metaverse.files.ro.scene;


import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rest Object информации о последнем обновлении сцены.
 *
 * @author Mikhail.Kataranov
 * @since 17.05.2025
 */
@Schema(description = "Объект информации о последнем обновлении сцены")
public class SceneUpdateInfoRO {

    @Schema(description = "Имя файла сцены")
    private String name;
    @Schema(description = "Дата обновления файла")
    private Date updateDate;

    /**
     * @return имя файла сцены
     */
    public String getName() {
        return name;
    }

    /**
     * @param name имя файла сцены
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return дата обновления файла
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate дата обновления файла
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
