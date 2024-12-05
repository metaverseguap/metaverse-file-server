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
    @Schema(description = "Пол аватара")
    private String gender;
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
