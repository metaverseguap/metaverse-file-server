package com.metaverse.files.ro.host.responses;


import com.metaverse.files.ro.host.HostRO;
import com.metaverse.files.ro.response.ResultDetailsRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Ответ на запрос информации о хосте.
 *
 * @author Mikhail.Kataranov
 * @since 04.05.2025
 */
@Schema(description = "Ответ на запрос информации о хосте")
public class HostResultRO extends ResultDetailsRO  {

    @Schema(description = "Информация о хосте")
    private HostRO host;

    /**
     * @return информация о хосте
     */
    public HostRO getHost() {
        return host;
    }

    /**
     * @param host информация о хосте
     */
    public void setHost(HostRO host) {
        this.host = host;
    }
}
