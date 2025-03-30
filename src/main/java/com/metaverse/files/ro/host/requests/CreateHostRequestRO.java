package com.metaverse.files.ro.host.requests;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rest Object запроса добавления хоста.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Schema(description = "Запрос добавления хоста")
public class CreateHostRequestRO {

    @Schema(description = "Название файла сцены, хостом которой является создаваемый хост")
    private String sceneName;

    /**
     * @return название файла сцены
     */
    public String getSceneName() {
        return sceneName;
    }

    /**
     * @param sceneName название файла сцены
     */
    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }
}
