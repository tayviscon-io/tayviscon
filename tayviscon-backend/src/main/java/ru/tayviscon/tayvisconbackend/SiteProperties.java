package ru.tayviscon.tayvisconbackend;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("tayviscon.site")
public class SiteProperties {

    private final Renderer renderer = new Renderer();
    private final Github github = new Github();

    public Renderer getRenderer() {
        return renderer;
    }

    public Github getGithub() {
        return github;
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

    public static class Github {
        private String organization;
        private String team;

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }
    }

}
