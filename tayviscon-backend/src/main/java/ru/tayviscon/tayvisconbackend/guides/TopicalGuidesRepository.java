package ru.tayviscon.tayvisconbackend.guides;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.tayviscon.tayvisconbackend.renderer.GuideContent;
import ru.tayviscon.tayvisconbackend.renderer.TayvisconRendererClient;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class TopicalGuidesRepository implements GuidesRepository<TopicalGuide> {

    private final TayvisconRendererClient rendererClient;

    public TopicalGuidesRepository(TayvisconRendererClient rendererClient) {
        this.rendererClient = rendererClient;
    }

    @Override
    public GuideHeader[] findAll() {
        return Arrays.stream(rendererClient.getTopicalGuides())
                .map(DefaultGuideHeader::new)
                .toArray(DefaultGuideHeader[]::new);
    }

    @Override
    public Optional<GuideHeader> findGuideHeaderByName(String name) {
        DefaultGuideHeader guideHeader = new DefaultGuideHeader(rendererClient.getTopicalGuide(name));
        return Optional.of(guideHeader);
    }

    @Override
    public TopicalGuide findByName(String name) {
        try{
            DefaultGuideHeader guideHeader = new DefaultGuideHeader(rendererClient.getTopicalGuide(name));
            GuideContent guideContent = rendererClient.getTopicalGuideContent(name);
            return new TopicalGuide(guideHeader, guideContent);
        } catch (Exception exception) {
            log.error("Не удалось отрисовать руководство Topical[" + name + "]");
            throw exception;
        }


    }
}
