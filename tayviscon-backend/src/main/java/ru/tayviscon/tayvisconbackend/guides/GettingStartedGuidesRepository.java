package ru.tayviscon.tayvisconbackend.guides;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tayviscon.tayvisconbackend.renderer.GuideContent;
import ru.tayviscon.tayvisconbackend.renderer.TayvisconRendererClient;

import java.util.Arrays;
import java.util.Optional;
@Slf4j
@Component
public class GettingStartedGuidesRepository implements GuidesRepository<GettingStartedGuide> {

    private final TayvisconRendererClient tayvisconRendererClient;

    public GettingStartedGuidesRepository(TayvisconRendererClient tayvisconRendererClient) {
        this.tayvisconRendererClient = tayvisconRendererClient;
    }

    @Override
    public GuideHeader[] findAll() {
        return Arrays.stream(tayvisconRendererClient.getGettingStartedGuides())
                .map(DefaultGuideHeader::new)
                .toArray(DefaultGuideHeader[]::new);
    }

    @Override
    public Optional<GuideHeader> findGuideHeaderByName(String name) {
        DefaultGuideHeader guideHeader = new DefaultGuideHeader(tayvisconRendererClient.getGettingStartedGuide(name));
        return Optional.of(guideHeader);
    }

    @Override
    public GettingStartedGuide findByName(String name) {
        try{
            DefaultGuideHeader guideHeader = new DefaultGuideHeader(tayvisconRendererClient.getGettingStartedGuide(name));
            GuideContent guideContent = tayvisconRendererClient.getGettingStartedGuideContent(name);
            return new GettingStartedGuide(guideHeader, guideContent);
        } catch (Exception exception) {
            log.error("Не удалось отрисовать руководство [" + name + "]" , exception);
            throw exception;
        }
    }
}
