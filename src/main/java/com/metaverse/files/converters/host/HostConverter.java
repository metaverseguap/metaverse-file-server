package com.metaverse.files.converters.host;

import com.metaverse.files.converters.Converter;
import com.metaverse.files.models.HostModel;
import com.metaverse.files.ro.host.HostRO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Конвертер HostModel в HostRO.
 *
 * @author Mikhail.Kataranov
 * @since 02.11.2024
 */
@Component
public class HostConverter extends Converter<HostModel, HostRO> {

    /**
     * Конструктор.
     */
    public HostConverter() {
        super();
        this.from = null;
        this.to = this::convert;
    }

    private HostRO convert(final HostModel host) {
        final HostRO ro = new HostRO();
        ro.setSceneName(host.getScene().getName());
        ro.setUri(host.getUri());
        if (!CollectionUtils.isEmpty(host.getUser())) {
            ro.setLogin(host.getUser().get(0).getLogin());
            ro.setName(host.getUser().get(0).getName());
        }

        return ro;
    }
}
