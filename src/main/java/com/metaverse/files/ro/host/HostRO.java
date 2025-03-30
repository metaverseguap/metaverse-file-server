package com.metaverse.files.ro.host;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rest Object хоста.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Schema(description = "Объект хоста")
public class HostRO {

    @Schema(description = "ip хоста, для подключения к нему через Mirror")
    private String hostIP;
    @Schema(description = "Порт хоста")
    private int port;
    @Schema(description = "Название файла сцены, хостом которой является данный хост")
    private String sceneName;
    @Schema(description = "Логин пользователя являющегося хостом")
    private String login;
    @Schema(description = "Отображаемое имя пользователя являющегося хостом")
    private String name;

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
     *
     * @param port порт хоста
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return название файла сцены
     */
    public String getSceneName() {
        return sceneName;
    }

    /**
     * @param sceneName название файла сцены
     */
    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    /**
     * @return логин пользователя являющегося хостом
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login логин пользователя являющегося хостом
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return отображаемое имя пользователя являющегося хостом
     */
    public String getName() {
        return name;
    }

    /**
     * @param name отображаемое имя пользователя являющегося хостом
     */
    public void setName(String name) {
        this.name = name;
    }
}
