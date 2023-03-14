package ru.tayviscon.tayvisconrenderer.github;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class Repository {

    private Long id;
    private String name;
    private String fullName;
    private String description;
    private String htmlUrl;
    private String getUrl;
    private String sshUrl;
    private String cloneUrl;
    private List<String> topics;

    @JsonCreator
    public Repository(@JsonProperty("id") Long id,
                      @JsonProperty("name")String name,
                      @JsonProperty("full_name")String fullName,
                      @JsonProperty("description")String description,
                      @JsonProperty("html_url")String htmlUrl,
                      @JsonProperty("git_url")String getUrl,
                      @JsonProperty("ssh_url")String sshUrl,
                      @JsonProperty("clone_url")String cloneUrl,
                      @JsonProperty("topics")List<String> topics) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.htmlUrl = htmlUrl;
        this.getUrl = getUrl;
        this.sshUrl = sshUrl;
        this.cloneUrl = cloneUrl;
        this.topics = topics;
    }
}
