package com.metaverse.files.converters.avatar;


import java.nio.file.Path;
import java.nio.file.Paths;

import com.metaverse.files.converters.Converter;
import com.metaverse.files.models.AvatarModel;
import com.metaverse.files.ro.avatar.AvatarInfoRO;
import com.metaverse.files.utils.FIleUtils;
import org.springframework.stereotype.Component;

/**
 * Конвертер AvatarModel в AvatarInfoRO.
 *
 * @author Mikhail.Kataranov
 * @since 28.11.2024
 */
@Component
public class AvatarInfoConverter extends Converter<AvatarModel, AvatarInfoRO> {

    /**
     * Конструктор.
     */
    public AvatarInfoConverter() {
        super();
        this.from = null;
        this.to = this::convert;
    }

    private AvatarInfoRO convert(final AvatarModel model) {
        AvatarInfoRO ro = new AvatarInfoRO();

        ro.setName(model.getName());
        ro.setDisplayName(model.getDisplayName());
        ro.setImageFilePath(model.getImageFilePath());

        Path imagePath = Paths.get(model.getImageFilePath()).normalize();
        ro.setImageData(FIleUtils.getBytesFromFile(imagePath.toFile()));

        return ro;
    }
}
