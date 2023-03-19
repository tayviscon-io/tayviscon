package ru.tayviscon.tayvisconbackend.renderer;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.tayviscon.tayvisconbackend.SiteProperties;

import java.net.URI;
import java.util.Arrays;

@Component
public class TayvisconRendererClient {

    private static final ParameterizedTypeReference<CollectionModel<GuideMetadata>> guidesResourceRef =
            new ParameterizedTypeReference<CollectionModel<GuideMetadata>>() {};
    private static final MediaType TEXT_ASCIIDOC = MediaType.parseMediaType("text/asciidoc");
    private final RestTemplate restTemplate;

    private final Traverson traverson;

    public TayvisconRendererClient(RestTemplateBuilder builder, SiteProperties properties) {
        this.restTemplate = builder
                .messageConverters(Traverson.getDefaultMessageConverters(MediaTypes.HAL_JSON))
                .build();
        this.traverson = new Traverson(URI.create(properties.getRenderer().getServiceUrl()), MediaTypes.HAL_JSON);
        this.traverson.setRestOperations(restTemplate);
    }

    public GuideMetadata[] getAllGuides() {
        return traverson.follow("guides").toObject(guidesResourceRef).getContent().toArray(new GuideMetadata[]{});
    }

    public GuideMetadata[] getGettingStartedGuides() {
        return Arrays.stream(getAllGuides())
                .filter(guide -> GuideType.GETTING_STARTED.equals(guide.getType()))
                .toArray(GuideMetadata[]::new);
    }

    public GuideMetadata getGettingStartedGuide(String name) {
        return this.traverson.follow("guides")
                .follow(Hop.rel("getting-started").withParameter("guide", name))
                .toObject(GuideMetadata.class);
    }

    public GuideContent getGettingStartedGuideContent(String name) {
        return this.traverson.follow("guides")
                .follow(Hop.rel("getting-started").withParameter("guide", name))
                .follow("content")
                .toObject(GuideContent.class);
    }

    public GuideMetadata[]  getTutorialGuides() {
        return Arrays.stream(getAllGuides())
                .filter(guide  -> GuideType.TUTORIAL.equals(guide.getType()))
                .toArray(GuideMetadata[]::new);
    }

    public GuideMetadata getTutorialGuide(String name) {
        return traverson.follow("guides")
                .follow(Hop.rel("tutorial").withParameter("guide", name))
                .toObject(GuideMetadata.class);
    }

    public GuideContent getTutorialGuideContent(String name) {
        return traverson.follow("guides")
                .follow(Hop.rel("tutorial").withParameter("guide", name))
                .follow("content")
                .toObject(GuideContent.class);
    }

    public GuideMetadata[] getTopicalGuides() {
        return Arrays.stream(getAllGuides())
                .filter(guide -> GuideType.TOPICAL.equals(guide.getType()))
                .toArray(GuideMetadata[]::new);
    }

    public GuideMetadata getTopicalGuide(String name) {
        return traverson.follow("guides")
                .follow(Hop.rel("topical").withParameter("guide", name))
                .toObject(GuideMetadata.class);
    }

    public GuideContent getTopicalGuideContent(String name) {
        return traverson.follow("guides")
                .follow(Hop.rel("topical").withParameter("guide", name))
                .follow("content")
                .toObject(GuideContent.class);
    }
}
