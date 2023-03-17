package ru.tayviscon.tayvisconbackend;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("tayviscon.site")
public class SiteProperties {

    private final Renderer renderer = new Renderer();

    public Renderer getRenderer() {
        return renderer;
    }

    public static class Renderer {
        private String serviceUrl = "http://localhost:8081";

        public String getServiceUrl() {
            return serviceUrl;
        }

        public void setServiceUrl(String serviceUrl) {
            this.serviceUrl = serviceUrl;
        }
    }

}
