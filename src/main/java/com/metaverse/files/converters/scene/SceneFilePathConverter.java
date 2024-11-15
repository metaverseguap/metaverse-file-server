package com.metaverse.files.converters.scene;


import com.metaverse.files.converters.Converter;
import com.metaverse.files.models.SceneModel;
import com.metaverse.files.ro.scene.SceneFilePathRO;
import org.springframework.stereotype.Component;

/**
 * Конвертер SceneModel в SceneFilePathRO.
 *
 * @author Mikhail.Kataranov
 * @since 05.12.2024
 */
@Component
public class SceneFilePathConverter extends Converter<SceneModel, SceneFilePathRO> {

    /**
     * Конструктор.
     */
    public SceneFilePathConverter() {
        super();
        this.from = null;
        this.to = this::convert;
    }

    private SceneFilePathRO convert(final SceneModel model) {
        SceneFilePathRO ro = new SceneFilePathRO();

        ro.setName(model.getName());
        ro.setFilePath(model.getFilePath());

        return ro;
    }
}
