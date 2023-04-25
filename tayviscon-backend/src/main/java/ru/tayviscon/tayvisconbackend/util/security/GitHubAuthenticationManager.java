package ru.tayviscon.tayvisconbackend.util.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GitHubAuthenticationManager implements AuthenticationManager {

    private final ClientRegistrationRepository clients;

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> githubMembers;

    public GitHubAuthenticationManager(ClientRegistrationRepository clients,
                                       OAuth2UserService<OAuth2UserRequest, OAuth2User> githubMembers) {
        this.clients = clients;
        this.githubMembers = githubMembers;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        if(!StringUtils.hasText(token)) {
            log.debug("Отсутствует токен OAuth2 в качестве основных учетных данных для аутентификации");
            return null;
        }
        ClientRegistration registration = this.clients.findByRegistrationId("github");
        OAuth2AccessToken oauthToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, token,
                Instant.now(), Instant.now().plus(Duration.ofHours(1)));
        OAuth2User oauthUser = this.githubMembers.loadUser(new OAuth2UserRequest(registration, oauthToken));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(oauthUser.getAuthorities());
        authorities.add(new SimpleGrantedAuthority("ROLE_API"));
        return new UsernamePasswordAuthenticationToken(oauthToken, null, authorities);
    }
}
