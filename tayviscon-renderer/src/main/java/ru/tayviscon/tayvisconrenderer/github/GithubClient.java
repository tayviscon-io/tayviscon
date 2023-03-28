package ru.tayviscon.tayvisconrenderer.github;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.tayviscon.tayvisconrenderer.RendererProperties;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class GithubClient {

    public static final String API_URI_BASE = "https://api.github.com";
    private static final Pattern NEXT_LINK_PATTERN = Pattern.compile(".*<([^>]*)>;\\s*rel=\"next\".*");
    private static final MediaType GITHUB_PREVIEW_TYPE = MediaType.parseMediaType("application/vnd.github.mercy-preview+json");
    private static final String RATE_LIMIT_PATH = "/rate_limit";
    private static final String REPOS_LIST_PATH = "/orgs/%s/repos?per_page=100";
    private static final String REPO_INFO_PATH = "/repos/{organization}/{repositoryName}";
    private static final String REPO_ZIPBALL_PATH = REPO_INFO_PATH + "/zipball";

    private final RestTemplate restTemplate;

    public GithubClient(RestTemplateBuilder restTemplateBuilder,
                        RendererProperties rendererProperties) {
        restTemplateBuilder = restTemplateBuilder
                .rootUri(API_URI_BASE)
                .additionalInterceptors(new GithubAcceptInterceptor());
        if (StringUtils.hasText(rendererProperties.getGithub().getToken())) {
            this.restTemplate = restTemplateBuilder
                    .additionalInterceptors(new GithubAppTokenInterceptor(rendererProperties.getGithub().getToken()))
                    .build();
        } else {
            log.warn("Доступ к Github API будет ограничен по скорости: 60 запросов в час");
            this.restTemplate = restTemplateBuilder.build();
        }

    }

    /**
     * Скачивает репозиотрий как ZIP архив
     *
     * @param organizationName название Github организации
     * @param repositoryName   название Github репозитория
     * @return ZIP архив в виде массива байтов
     */
    public byte[] downloadRepositoryAsZipBall(String organizationName, String repositoryName) {
        try {
            byte[] response = this.restTemplate.getForObject(REPO_ZIPBALL_PATH, byte[].class, organizationName, repositoryName);
            return response;
        } catch (HttpClientErrorException exception) {
            throw new GithubResourceNotFoundException(organizationName, exception);
        }
    }

    /**
     * Получает список доступных репозиториев по заданной организации
     *
     * @param organizationName название Github организации
     * @return список всех репозиториев организации
     */
    public List<Repository> getOrganizationRepositories(String organizationName) {
        List<Repository> repositories = new ArrayList<>();
        Optional<String> nextPage = Optional.of(String.format(REPOS_LIST_PATH, organizationName));
        while (nextPage.isPresent()) {
            ResponseEntity<Repository[]> page = this.restTemplate.getForEntity(nextPage.get(), Repository[].class, organizationName);
            repositories.addAll(Arrays.asList(page.getBody()));
            nextPage = findNextPageLink(page);
        }
        return repositories;
    }

    /**
     * Получает информацию о репозитории по заданной организации
     *
     * @param organizationName название Github организации
     * @param repositoryName   название Github репозитория
     * @return информацию о репозитории
     */
    public Repository getOrganizationRepository(String organizationName, String repositoryName) {
        try {
            Repository repository = this.restTemplate.getForObject(REPO_INFO_PATH, Repository.class, organizationName, repositoryName);
            Assert.state(repository.getFullName().contains(repositoryName),
                    () -> "Репозиторий [" + repositoryName + "] перенаправлен на [" + repository.getFullName() + "]");
            return repository;
        } catch (HttpClientErrorException | IllegalStateException exception) {
            throw new GithubResourceNotFoundException(organizationName, repositoryName, exception);
        }
    }

    private Optional<String> findNextPageLink(ResponseEntity response) {
        List<String> links = response.getHeaders().get("Link");
        if (links == null) {
            return Optional.empty();
        }
        return links.stream()
                .map(NEXT_LINK_PATTERN::matcher)
                .filter(Matcher::matches)
                .map(matcher -> matcher.group(1))
                .findFirst();
    }

    public RateLimit getRateLimitInfo() {
        return this.restTemplate.getForObject(RATE_LIMIT_PATH, RateLimit.class);
    }

    private static class GithubAppTokenInterceptor implements ClientHttpRequestInterceptor {
        private final String token;

        public GithubAppTokenInterceptor(String token) {
            this.token = token;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            if (StringUtils.hasText(this.token)) {
                request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Token " + this.token);
            }
            return execution.execute(request, body);
        }
    }

    private static class GithubAcceptInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().setAccept(Collections.singletonList(GITHUB_PREVIEW_TYPE));
            return execution.execute(request, body);
        }
    }
}
