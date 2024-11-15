package com.metaverse.files.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Hibernate модель представляющая объект Avatar.
 *
 * @author Mikhail.Kataranov
 * @since 28.11.2024
 */
@Entity
@Table(name = "avatars")
public class AvatarModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "image_file_path")
    private String imageFilePath;

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return имя файла
     */
    public String getName() {
        return name;
    }

    /**
     * @param name имя файла
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return отображаемое имя файла
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName отображаемое имя файла
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
     * @return путь хранения файла аватара на диске
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath путь хранения файла аватара на диске
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return путь хранения файла изображения сцены на диске
     */
    public String getImageFilePath() {
        return imageFilePath;
    }

    /**
     * @param imageFilePath путь хранения файла изображения сцены на диске
     */
    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }
}
