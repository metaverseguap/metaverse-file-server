package com.metaverse.files.ro.avatar;


import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rest Object информации об аватаре.
 *
 * @author Mikhail.Kataranov
 * @since 28.11.2024
 */
@Schema(description = "Объект информации об аватаре")
public class AvatarInfoRO {

    @Schema(description = "Имя файла аватара")
    private String name;
    @Schema(description = "Отображаемое имя аватара")
    private String displayName;
    @Schema(description = "Тип контроллера анимации")
    private String animationControllerType;
    @Schema(description = "Путь до файла изображения аватара на сервере")
    private String imageFilePath;
    @Schema(description = "Изображение аватара")
    private byte[] imageData;
    @Schema(description = "Дата обновления файла")
    private Date updateDate;

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
     * @return отображаемое имя аватара
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName отображаемое имя аватара
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
     * @return путь до файла изображения аватара на сервере
     */
    public String getImageFilePath() {
        return imageFilePath;
    }

    /**
     * @param imageFilePath путь до файла изображения аватара на сервере
     */
    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    /**
     * @return изображение аватара
     */
    public byte[] getImageData() {
        return imageData;
    }

    /**
     * @param imageData изображение аватара
     */
    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
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
