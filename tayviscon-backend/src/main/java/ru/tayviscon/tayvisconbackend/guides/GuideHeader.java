package ru.tayviscon.tayvisconbackend.guides;

import java.util.Set;

public interface GuideHeader {

    String getName();
    String getRepositoryName();
    String getTitle();
    String getDescription();
    String getGithubUrl();
    String getGitUrl();
    String getSshUrl();
    String getCloneUrl();
    Set<String> getProjects();
    String getZipUrl();
    String getCiStatusImageUrl();
    String getCiLatestUrl();

}
