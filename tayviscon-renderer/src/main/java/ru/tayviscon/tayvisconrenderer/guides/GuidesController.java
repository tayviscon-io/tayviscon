package ru.tayviscon.tayvisconrenderer.guides;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tayviscon.tayvisconrenderer.RendererProperties;
import ru.tayviscon.tayvisconrenderer.github.GithubClient;
import ru.tayviscon.tayvisconrenderer.github.GithubResourceNotFoundException;
import ru.tayviscon.tayvisconrenderer.github.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/guides", produces = MediaTypes.HAL_JSON_VALUE)
public class GuidesController {

    private final GuideRenderer guideRenderer;

    private final GithubClient githubClient;

    private final RendererProperties rendererProperties;
    private final GuideModelAssembler guideModelAssembler = new GuideModelAssembler();

    public GuidesController(GuideRenderer guideRenderer, GithubClient githubClient, RendererProperties rendererProperties) {
        this.guideRenderer = guideRenderer;
        this.githubClient = githubClient;
        this.rendererProperties = rendererProperties;
    }

    @ExceptionHandler(GithubResourceNotFoundException.class)
    public ResponseEntity resourceNotFound() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public CollectionModel<GuideModel> listGuides() {
        List<GuideModel> guideModels = guideModelAssembler
                .toCollectionModel(githubClient.getOrganizationRepositories(rendererProperties.getGuides().getOrganization()))
                .getContent()
                .stream().filter(guide -> !guide.getGuideType().equals(GuideType.UNKNOWN))
                .collect(Collectors.toList());
        CollectionModel<GuideModel> resources = CollectionModel.of(guideModels);

        for (GuideType type : GuideType.values()) {
            if(! GuideType.UNKNOWN.equals(type)) {
                resources.add(linkTo(methodOn(GuidesController.class).showGuide(type.getSlug(), null)).withRel(type.getSlug()));
            }
        }
        return resources;
    }

    @GetMapping(path = "/{type}/{guide}")
    public ResponseEntity<GuideModel> showGuide(@PathVariable String type, @PathVariable String guide) {
        GuideType guideType = GuideType.fromSlug(type);
        if(GuideType.UNKNOWN.equals(guideType)) {
            return ResponseEntity.notFound().build();
        }
        Repository repository = githubClient.getOrganizationRepository(rendererProperties.getGuides().getOrganization(), guideType.getPrefix() + guide);
        GuideModel guideModel = guideModelAssembler.toModel(repository);
        if(guideModel.getGuideType().equals(GuideType.UNKNOWN)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(guideModel);
    }

    @GetMapping(path = "/{type}/{guide}/content")
    public ResponseEntity<GuideContentModel> renderGuide(@PathVariable String type, @PathVariable String guide) {
        GuideType guideType = GuideType.fromSlug(type);
        if(GuideType.UNKNOWN.equals(guideType)) {
            return ResponseEntity.notFound().build();
        }
        GuideContentModel guideContentModel = guideRenderer.render(guideType, guide);
        guideContentModel.add(linkTo(methodOn(GuidesController.class).renderGuide(guideType.getSlug(), guide)).withSelfRel());
        guideContentModel.add(linkTo(methodOn(GuidesController.class).showGuide(guideType.getSlug(), guide)).withRel("guide"));
        return ResponseEntity.ok(guideContentModel);
    }
}
