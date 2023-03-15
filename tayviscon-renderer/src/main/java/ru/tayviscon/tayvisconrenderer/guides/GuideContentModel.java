package ru.tayviscon.tayvisconrenderer.guides;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class GuideContentModel extends RepresentationModel {

    private String name;
    private String tableOfContent;
    private String content;
    public GuideContentModel(String name, String content, String tableOfContent) {
        this.name = name;
        this.content = content;
        this.tableOfContent = tableOfContent;
    }
    public GuideContentModel() {}

}
