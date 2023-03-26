package ru.tayviscon.tayvisconbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.tayviscon.tayvisconbackend.util.StaticPagesPathFinder;

import java.io.IOException;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private StaticPagesPathFinder staticPagesPathFinder;

    @Bean
    public StaticPagesPathFinder staticPagesPathFinder(ResourcePatternResolver resourcePatternResolver) {
        this.staticPagesPathFinder = new StaticPagesPathFinder(resourcePatternResolver);
        return this.staticPagesPathFinder;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        try{
            for (StaticPagesPathFinder.PagePath paths: staticPagesPathFinder.findPaths()) {
                String urlPath = paths.getUrlPath();
                registry.addViewController(urlPath).setViewName("pages" + paths.getFilePath());
                if(!urlPath.isEmpty()) {
                    registry.addViewController(urlPath + "/").setViewName("pages" + paths.getFilePath());
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException("Не удалось найти статическую страницу " + exception.getMessage(), exception);
        }
    }
}
