package com.metaverse.files.ro.avatar;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rest Object пути к файлу аватара.
 *
 * @author Mikhail.Kataranov
 * @since 04.12.2024
 */
@Schema(description = "Объект пути к файлу аватара")
public class AvatarFilePathRO {

    @Schema(description = "Имя файла аватара")
    private String name;
    @Schema(description = "Тип контроллера анимации")
    private String animationControllerType;
    @Schema(description = "Путь до файла аватара на сервере")
    private String filePath;

    /**
     * @return имя файла аватара
     */
    public String getName() {
        return name;
    }

    /**
     * @param name имя файла аватара
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return тип контроллера анимации
     */
    public String getAnimationControllerType() {
        return animationControllerType;
    }

    /**
     * @param animationControllerType тип контроллера анимации
     */
    public void setAnimationControllerType(String animationControllerType) {
        this.animationControllerType = animationControllerType;
    }

    /**
     * @return путь до файла аватара на сервере
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath путь до файла аватара на сервере
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
