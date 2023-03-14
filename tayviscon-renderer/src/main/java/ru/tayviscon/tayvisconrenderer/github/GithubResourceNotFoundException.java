package ru.tayviscon.tayvisconrenderer.github;

public class GithubResourceNotFoundException extends RuntimeException{

    private final String resourceName;

    public GithubResourceNotFoundException(String organizationName, String repositoryName, Throwable cause) {
        super("Не удалось найти Github репозиторий [" + organizationName + "/" + repositoryName + "]", cause);
        this.resourceName = "Репозиторий [" + organizationName + "/" + repositoryName + "]";
    }

    public GithubResourceNotFoundException(String organizationName, Throwable cause) {
        super("Не удалось найти Github организацию [" + organizationName + "]", cause);
        this.resourceName = "Организация [" + organizationName + "]";
    }

    public String getResourceName() {
        return this.resourceName;
    }

}
