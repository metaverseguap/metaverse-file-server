package com.metaverse.files.converters.avatar;


import com.metaverse.files.converters.Converter;
import com.metaverse.files.models.AvatarModel;
import com.metaverse.files.ro.avatar.AvatarFilePathRO;
import org.springframework.stereotype.Component;

/**
 * Конвертер AvatarModel в AvatarFileRO.
 *
 * @author Mikhail.Kataranov
 * @since 04.12.2024
 */
@Component
public class AvatarFilePathConverter extends Converter<AvatarModel, AvatarFilePathRO> {

    /**
     * Конструктор.
     */
    public AvatarFilePathConverter() {
        super();
        this.from = null;
        this.to = this::convert;
    }

    private AvatarFilePathRO convert(final AvatarModel model) {
        AvatarFilePathRO ro = new AvatarFilePathRO();

        ro.setName(model.getName());
        ro.setGender(model.getGender());
        ro.setFilePath(model.getFilePath());

        return ro;
    }
}
