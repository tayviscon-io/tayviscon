package ru.tayviscon.tayvisconrenderer;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("tayviscon.renderer")
public class RendererProperties {

    private final Guides guides = new Guides();

    public Guides getGuides() {
        return this.guides;
    }
    public static class Guides {

        /**
         * Название Github организации, из которой будут получены руководства
         */
        private String organization = "tayviscon-guides";
        public String getOrganization() {
            return organization;
        }
        public void setOrganization(String organization) {
            this.organization = organization;
        }
    }

}
