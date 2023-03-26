package ru.tayviscon.tayvisconbackend;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tayviscon.tayvisconbackend.guides.GettingStartedGuidesRepository;
import ru.tayviscon.tayvisconbackend.guides.TopicalGuidesRepository;
import ru.tayviscon.tayvisconbackend.guides.TutorialGuidesRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager simpleCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(
                Stream.of(GettingStartedGuidesRepository.CACHE_GUIDES, GettingStartedGuidesRepository.CACHE_GUIDE,
                                TutorialGuidesRepository.CACHE_TUTORIALS, TutorialGuidesRepository.CACHE_TUTORIAL,
                                TopicalGuidesRepository.CACHE_TOPICALS, TopicalGuidesRepository.CACHE_TOPICAL)
                        .map(ConcurrentMapCache::new)
                        .collect(Collectors.toList()));
        return cacheManager;

    }

}
