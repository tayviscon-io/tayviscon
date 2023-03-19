package ru.tayviscon.tayvisconbackend.renderer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GuideContent {

    private String repositoryName;
    private String tableOfContent;
    private String content;

    @JsonCreator
    public GuideContent(@JsonProperty("repositoryName") String repositoryName,
                        @JsonProperty("tableOfContent") String tableOfContent,
                        @JsonProperty("content") String content) {
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
