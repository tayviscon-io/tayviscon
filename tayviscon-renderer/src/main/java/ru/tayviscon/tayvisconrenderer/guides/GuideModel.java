package ru.tayviscon.tayvisconrenderer.guides;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.tayviscon.tayvisconrenderer.github.Repository;

/**
 * Руководства Tayviscon ("Getting started guide", "Topical guide", "Tutorial") - это документы
 * для изучения информационных технологий, изучение которых сообщество Tayviscon считает обязательным.
 * Поддерживается на основании Github репозитория.
 * @see GuideType
 * @see ru.tayviscon.tayvisconrenderer.RendererProperties
 */
@Getter
@Relation(collectionRelation = "guides")
public class GuideModel extends RepresentationModel<GuideModel> {

    private String name;
    private String repositoryName;
    private String title;
    private String description;
    private GuideType guideType;
    private String githubUrl;
    private String gitUrl;
    private String sshUrl;
    private String cloneUrl;
    private String[] projects;

    public GuideModel(Repository repository) {
        this.guideType = GuideType.fromRepositoryName(repository.getName());
        this.name = this.guideType.stripPrefix(repository.getName());
        this.repositoryName = repository.getFullName();
        this.description = repository.getDescription();
        if (description != null) {
            String[] split = repository.getDescription().split("::");
            this.title = split[0].trim();
            this.description = (split.length > 1) ? split[1].trim() : "";
        } else {
            this.title = "";
            this.description = "";
        }
        this.githubUrl = repository.getHtmlUrl();
        this.gitUrl = repository.getGitUrl();
        this.sshUrl = repository.getSshUrl();
        this.cloneUrl = repository.getCloneUrl();
        if (repository.getTopics() != null) {
            this.projects = repository.getTopics().toArray(new String[0]);
        } else {
            this.projects = new String[0];
        }
    }
}
