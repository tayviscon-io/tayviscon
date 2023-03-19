package ru.tayviscon.tayvisconbackend.guides;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tayviscon.tayvisconbackend.renderer.GuideContent;
import ru.tayviscon.tayvisconbackend.renderer.TayvisconRendererClient;

import java.util.Arrays;
import java.util.Optional;
@Slf4j
@Component
public class TutorialGuidesRepository implements GuidesRepository<TutorialGuide>{

    private final TayvisconRendererClient rendererClient;

    public TutorialGuidesRepository(TayvisconRendererClient rendererClient) {
        this.rendererClient = rendererClient;
    }

    @Override
    public GuideHeader[] findAll() {
        return Arrays.stream(rendererClient.getTutorialGuides())
                .map(DefaultGuideHeader::new)
                .toArray(DefaultGuideHeader[]::new);
    }

    @Override
    public Optional<GuideHeader> findGuideHeaderByName(String name) {
        DefaultGuideHeader  guideHeader = new DefaultGuideHeader(rendererClient.getTutorialGuide(name));
        return Optional.of(guideHeader);
    }

    @Override
    public TutorialGuide findByName(String name) {
        try {
            DefaultGuideHeader guideHeader = new DefaultGuideHeader(rendererClient.getTutorialGuide(name));
            GuideContent guideContent =  rendererClient.getTutorialGuideContent(name);
            return new TutorialGuide(guideHeader, guideContent);

        } catch (Exception  exception) {
            log.error("Не удалось отрисовать руководство [" + name + "]");
            throw exception;
        }
    }
}
