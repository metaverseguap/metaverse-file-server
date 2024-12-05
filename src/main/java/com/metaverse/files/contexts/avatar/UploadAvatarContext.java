package com.metaverse.files.contexts.avatar;


import org.springframework.web.multipart.MultipartFile;

/**
 * Класс хранящий данные необходимые для загрузки на сервер файла аватара.
 *
 * @author Mikhail.Kataranov
 * @since 28.11.2024
 */
public class UploadAvatarContext {

    private final String displayName;
    private final String gender;
    private final MultipartFile avatar;
    private final MultipartFile image;

    private UploadAvatarContext(UploadAvatarContextBuilder builder) {
        this.displayName = builder.displayName;
        this.gender = builder.gender;
        this.avatar = builder.avatar;
        this.image = builder.image;
    }

    /**
     * @return отображаемое имя аватара
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return пол аватара
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return файл аватара
     */
    public MultipartFile getAvatar() {
        return avatar;
    }

    /**
     * Есть ли в контексте файл аватара.
     *
     * @return true, если в контексте есть файл аватара
     */
    public boolean hasScene() {
        return avatar != null && !avatar.isEmpty();
    }

    /**
     * @return файл изображения аватара
     */
    public MultipartFile getImage() {
        return image;
    }

    /**
     * Есть ли в контексте файл изображения.
     *
     * @return true, если в контексте есть файл изображения
     */
    public boolean hasImage() {
        return image != null && !image.isEmpty();
    }

    /**
     * @return builder
     */
    public static UploadAvatarContextBuilder builder() {
        return new UploadAvatarContextBuilder();
    }

    /**
     * Builder.
     */
    public static class UploadAvatarContextBuilder {

        private String displayName;
        private String gender;
        private MultipartFile avatar;
        private MultipartFile image;

        private UploadAvatarContextBuilder() {
        }

        private UploadAvatarContextBuilder self() {
            return this;
        }

        /**
         * @param displayName отображаемое имя аватара
         * @return self
         */
        public UploadAvatarContextBuilder displayName(String displayName) {
            this.displayName = displayName;
            return self();
        }

        /**
         * @param gender пол аватара
         * @return self
         */
        public UploadAvatarContextBuilder gender(String gender) {
            this.gender = gender;
            return self();
        }

        /**
         * @param avatar файл аватара
         * @return self
         */
        public UploadAvatarContextBuilder avatar(MultipartFile avatar) {
            this.avatar = avatar;
            return self();
        }

        /**
         * @param image файл изображения аватара
         * @return self
         */
        public UploadAvatarContextBuilder image(MultipartFile image) {
            this.image = image;
            return self();
        }

        /**
         * Собрать объект.
         *
         * @return собранный объект
         */
        public UploadAvatarContext build() {
            return new UploadAvatarContext(this);
        }
    }
}
