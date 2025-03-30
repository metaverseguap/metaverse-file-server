package com.metaverse.files.ro.host;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Полный адрес хоста.
 *
 * @author Mikhail.Kataranov
 * @since 01.03.2025
 */
@Schema(description = "Адрес хоста")
public class HostAddressRO {

    @Schema(description = "IP хоста")
    private String hostIP;
    @Schema(description = "Порт")
    private int port;

    /**
     * @return ip хоста
     */
    public String getHostIP() {
        return hostIP;
    }

    /**
     * @param hostIP ip хоста
     */
    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    /**
     * @return порт хоста
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port порт хоста
     */
    public void setPort(int port) {
        this.port = port;
    }
}
