package ru.tayviscon.tayvisconbackend.guides;

import org.springframework.util.Assert;
import ru.tayviscon.tayvisconbackend.renderer.GuideContent;

import java.util.Set;

public abstract class AbstractGuide implements Guide {

    private GuideHeader header;
    private String content;
    private String tableOfContent;
    private String typeLabel;

    public AbstractGuide() {
    }

    public AbstractGuide (String typeLabel, GuideHeader header, GuideContent content) {
        this.typeLabel = typeLabel;
        this.header = header;
        this.content = content.getContent();
        this.tableOfContent = content.getTableOfContent();
    }

    public GuideHeader getHeader() {
        return this.header;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public String getTableOfContent() {
        return this.tableOfContent;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        Assert.notNull(typeLabel, "Тип руководства должен быть заполнен");
        this.typeLabel = typeLabel;
    }

    @Override
    public String getName() {
        return this.header.getName();
    }

    @Override
    public String getRepositoryName() {
        return this.header.getRepositoryName();
    }

    @Override
    public String getTitle() {
        return this.header.getTitle();
    }

    @Override
    public String getDescription() {
        return this.header.getDescription();
    }

    @Override
    public String getGithubUrl() {
        return this.header.getGithubUrl();
    }

    @Override
    public String getGitUrl() {
        return this.header.getGitUrl();
    }

    @Override
    public String getSshUrl() {
        return this.header.getSshUrl();
    }

    @Override
    public String getCloneUrl() {
        return this.header.getCloneUrl();
    }

    @Override
    public Set<String> getProjects() {
        return this.header.getProjects();
    }

    @Override
    public String getZipUrl() {
        return this.header.getZipUrl();
    }

    @Override
    public String getCiStatusImageUrl() {
        return this.header.getCiStatusImageUrl();
    }

    @Override
    public String getCiLatestUrl() {
        return this.header.getCiLatestUrl();
    }
}
