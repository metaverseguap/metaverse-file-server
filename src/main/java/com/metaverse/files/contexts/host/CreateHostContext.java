package com.metaverse.files.contexts.host;

/**
 * Класс хранящий данные необходимые для создания хоста.
 *
 * @author Mikhail.Kataranov
 * @since 02.11.2024
 */
public class CreateHostContext {

    private final String hostIP;
    private final String sceneName;

    private CreateHostContext(CreateHostContextBuilder builder) {
        this.hostIP = builder.hostIP;
        this.sceneName = builder.sceneName;
    }

    /**
     * @return ip хоста
     */
    public String getHostIP() {
        return hostIP;
    }

    /**
     * @return название файла сцены
     */
    public String getSceneName() {
        return sceneName;
    }

    /**
     * @return builder
     */
    public static CreateHostContextBuilder builder() {
        return new CreateHostContextBuilder();
    }

    /**
     * Builder.
     */
    public static class CreateHostContextBuilder {

        private String hostIP;
        private String sceneName;

        private CreateHostContextBuilder() {
        }

        private CreateHostContextBuilder self() {
            return this;
        }

        /**
         * @param hostIP ip хоста
         * @return self
         */
        public CreateHostContextBuilder hostIP(String hostIP) {
            this.hostIP = hostIP;
            return self();
        }

        /**
         * @param sceneName название файла сцены
         * @return self
         */
        public CreateHostContextBuilder sceneName(String sceneName) {
            this.sceneName = sceneName;
            return self();
        }

        /**
         * Собрать объект.
         *
         * @return собранный объект
         */
        public CreateHostContext build() {
            return new CreateHostContext(this);
        }
    }
}
