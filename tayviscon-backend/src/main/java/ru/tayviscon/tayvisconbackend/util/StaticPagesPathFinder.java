package ru.tayviscon.tayvisconbackend.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaticPagesPathFinder {

    private ResourcePatternResolver resourcePatternResolver;

    public static class PagePath {
        private String filePath;
        private String urlPath;

        public PagePath(String filePath, String urlPath) {
            this.filePath = filePath;
            this.urlPath = urlPath;
        }

        public String getFilePath() {
            return filePath;
        }

        public String getUrlPath() {
            return urlPath;
        }
    }

    public StaticPagesPathFinder(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

    public List<PagePath> findPaths() throws IOException {
        Resource baseResource = resourcePatternResolver.getResource("classpath:/templates/pages");
        String basePath = baseResource.getURL().getPath();
        Resource[] resources = resourcePatternResolver.getResources("classpath:/templates/pages/**/*.html");
        List<PagePath> paths = new ArrayList<>();
        for (Resource resource : resources) {
            String filePath = relativeFilePath(basePath, resource);
            paths.add(new PagePath(filePath, buildRequestMapping(filePath)));
        }
        return paths;
    }

    private String relativeFilePath(String basePath, Resource resource) throws IOException {
        return resource.getURL().getPath().substring(basePath.length()).replace(".html","");
    }

    private String  buildRequestMapping(String filePath) {
        String requestMapping = filePath;
        if(requestMapping.endsWith("/index")) {
            requestMapping = requestMapping.replace("/index", "");
            if(requestMapping.equals("")) {
                requestMapping = "/";
            }
        }
        return requestMapping;
    }
}
