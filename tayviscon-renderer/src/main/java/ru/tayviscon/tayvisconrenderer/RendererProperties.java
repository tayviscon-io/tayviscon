package ru.tayviscon.tayvisconrenderer;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.MatchesPattern;

@ConfigurationProperties("tayviscon.renderer")
public class RendererProperties {

    private final Guides guides = new Guides();

    private final Github github = new Github();

    public Github getGithub() {
        return github;
    }

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

    public static class Github{
        /**
         * Токен публичного доступа к Github API
         */
        @MatchesPattern("([0-9a-zA-Z_]*)?")
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
