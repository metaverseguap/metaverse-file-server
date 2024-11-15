package com.metaverse.files.converters.scene;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.metaverse.files.converters.Converter;
import com.metaverse.files.models.SceneModel;
import com.metaverse.files.ro.scene.SceneInfoRO;
import com.metaverse.files.utils.FIleUtils;
import org.springframework.stereotype.Component;

/**
 * Конвертер SceneModel в SceneInfoRO.
 *
 * @author Mikhail.Kataranov
 * @since 02.11.2024
 */
@Component
public class SceneInfoConverter extends Converter<SceneModel, SceneInfoRO> {

    /**
     * Конструктор.
     */
    public SceneInfoConverter() {
        super();
        this.from = null;
        this.to = this::convert;
    }

    private SceneInfoRO convert(final SceneModel model) {
        SceneInfoRO ro = new SceneInfoRO();

        ro.setSortIndex(model.getSortIndex());
        ro.setName(model.getName());
        ro.setDisplayName(model.getDisplayName());
        ro.setDevice(model.getDevice());
        ro.setImageFilePath(model.getImageFilePath());

        Path imagePath = Paths.get(model.getImageFilePath()).normalize();
        ro.setImageData(FIleUtils.getBytesFromFile(imagePath.toFile()));

        return ro;
    }
}
