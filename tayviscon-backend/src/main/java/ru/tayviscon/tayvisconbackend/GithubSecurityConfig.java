package ru.tayviscon.tayvisconbackend;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import ru.tayviscon.tayvisconbackend.util.security.GitHubAuthenticationManager;
import ru.tayviscon.tayvisconbackend.util.security.GithubMemberOAuth2UserService;

@Configuration
public class GithubSecurityConfig {

    @Bean
    public GitHubAuthenticationManager gitHubAuthenticationManager(ClientRegistrationRepository oauthClient,
                                                                   GithubMemberOAuth2UserService userService) {
        return new GitHubAuthenticationManager(oauthClient, userService);
    }

    @Bean
    public GithubMemberOAuth2UserService githubMemberOAuth2UserService(RestTemplateBuilder builder, SiteProperties properties) {
        return new GithubMemberOAuth2UserService(builder, properties.getGithub().getOrganization(), properties.getGithub().getTeam());
    }

}
