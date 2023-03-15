package ru.tayviscon.tayvisconrenderer.guides;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import ru.tayviscon.tayvisconrenderer.github.Repository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class GuideModelAssembler extends RepresentationModelAssemblerSupport<Repository, GuideModel> {

    public GuideModelAssembler() {
        super(GuidesController.class, GuideModel.class);
    }

    @Override
    public GuideModel toModel(Repository repository) {
        GuideModel resource = new GuideModel(repository);
        resource.add(linkTo(methodOn(GuidesController.class).showGuide(resource.getGuideType().getSlug(), resource.getName())).withSelfRel());
        resource.add(linkTo(methodOn(GuidesController.class).renderGuide(resource.getGuideType().getSlug(), resource.getName())).withRel("content"));
        resource.add(linkTo(methodOn(GuidesController.class).listGuides()).withRel("guides"));
        return resource;
    }
}
