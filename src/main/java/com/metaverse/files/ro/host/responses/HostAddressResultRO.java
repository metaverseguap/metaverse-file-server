package com.metaverse.files.ro.host.responses;


import com.metaverse.files.ro.host.HostAddressRO;
import com.metaverse.files.ro.responses.ResultDetailsRO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Ответ на запрос создания хоста, содержащий адрес хоста.
 *
 * @author Mikhail.Kataranov
 * @since 23.02.2025
 */
@Schema(description = "Ответ на запрос создания хоста, содержащий адрес хоста")
public class HostAddressResultRO extends ResultDetailsRO {

    @Schema(description = "Адрес хоста")
    private HostAddressRO hostAddress;

    /**
     * @return адрес хоста
     */
    public HostAddressRO getHostAddress() {
        return hostAddress;
    }

    /**
     * @param hostAddress адрес хоста
     */
    public void setHostAddress(HostAddressRO hostAddress) {
        this.hostAddress = hostAddress;
    }
}
