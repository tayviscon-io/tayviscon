package ru.tayviscon.tayvisconbackend.guides;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import ru.tayviscon.tayvisconbackend.SiteProperties;
import ru.tayviscon.tayvisconbackend.renderer.TayvisconRendererClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest({GettingStartedGuidesRepository.class, TayvisconRendererClient.class, SiteProperties.class})
@TestPropertySource(properties = "tayviscon.site.renderer.service-url=https://example.com/")
public class GuidesRepositoryTest {

    @Autowired
    private GettingStartedGuidesRepository repository;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void findAllShouldReturnOnlyGettingStartedGuides() {
        this.server.expect(requestTo("https://example.com/"))
                .andRespond(withSuccess(getClassPathResource("root.json"), MediaTypes.HAL_JSON));
        this.server.expect(requestTo("/guides"))
                .andRespond(withSuccess(getClassPathResource("guides.json"),MediaTypes.HAL_JSON));

        assertThat(this.repository.findAll()).extracting("name").containsExactlyInAnyOrder("tayviscon-guide-1",  "tayviscon-guide-2");
    }

    private ClassPathResource getClassPathResource(String path) {
        return new ClassPathResource(path, TayvisconRendererClient.class);
    }

}
