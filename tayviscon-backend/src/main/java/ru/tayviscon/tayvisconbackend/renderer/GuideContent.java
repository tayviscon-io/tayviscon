package ru.tayviscon.tayvisconbackend.renderer;

public class GuideContent {

    private String repositoryName;
    private String tableOfContent;
    private String content;

    public GuideContent(String repositoryName, String tableOfContent, String content) {
        this.repositoryName = repositoryName;
        this.tableOfContent = tableOfContent;
        this.content = content;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getTableOfContent() {
        return tableOfContent;
    }

    public String getContent() {
        return content;
    }
}
