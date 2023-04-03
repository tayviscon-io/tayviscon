package ru.tayviscon.tayvisconrenderer;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tayviscon.tayvisconrenderer.guides.GuidesController;
import ru.tayviscon.tayvisconrenderer.markup.MarkupController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class IndexController {

    @GetMapping("/")
    public RepresentationModel index() {
        RepresentationModel resource = new RepresentationModel();
        resource.add(linkTo(methodOn(MarkupController.class).renderMarkup(MediaType.TEXT_MARKDOWN, "")).withRel("markup"));
        resource.add(linkTo(methodOn(GuidesController.class).listGuides()).withRel("guides"));
        return resource;
    }

}
