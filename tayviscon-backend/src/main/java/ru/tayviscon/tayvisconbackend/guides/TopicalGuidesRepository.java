package ru.tayviscon.tayvisconbackend.guides;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.tayviscon.tayvisconbackend.renderer.GuideContent;
import ru.tayviscon.tayvisconbackend.renderer.TayvisconRendererClient;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class TopicalGuidesRepository implements GuidesRepository<TopicalGuide> {

    private final TayvisconRendererClient rendererClient;
    public static final String CACHE_TOPICALS = "cache.topicals";
    public static final String CACHE_TOPICAL = "cache.topical";

    public TopicalGuidesRepository(TayvisconRendererClient rendererClient) {
        this.rendererClient = rendererClient;
    }

    @Override
    @Cacheable(CACHE_TOPICALS)
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
    @Cacheable(CACHE_TOPICAL)
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

    @CacheEvict(CACHE_TOPICALS)
    public void evictListFromCache() {
        log.info("Руководства были удалены из кеша");
    }
    @CacheEvict(CACHE_TOPICAL)
    public void evictFromCache(String  guide)  {
        log.info("Руководство было удалено из кеша: {}", guide);
    }
}
