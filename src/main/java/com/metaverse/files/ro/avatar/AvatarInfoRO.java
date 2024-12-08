package com.metaverse.files.ro.avatar;


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
    @Schema(description = "Пол аватара")
    private String gender;
    @Schema(description = "Путь до файла изображения аватара на сервере")
    private String imageFilePath;
    @Schema(description = "Изображение аватара")
    private byte[] imageData;

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
     * @return пол аватара
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender пол аватара
     */
    public void setGender(String gender) {
        this.gender = gender;
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
}
