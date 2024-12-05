package com.metaverse.files.ro.scene;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rest Object пути к файлу сцены.
 *
 * @author Mikhail.Kataranov
 * @since 05.12.2024
 */
@Schema(description = "Объект пути к файлу сцены")
public class SceneFilePathRO {

    @Schema(description = "Имя файла сцены")
    private String name;
    @Schema(description = "Путь до файла сцены на сервере")
    private String filePath;

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
     * @return путь до файла сцены на сервере
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath путь до файла сцены на сервере
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
