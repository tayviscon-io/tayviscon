package ru.tayviscon.tayvisconbackend.util.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.stereotype.Component;
import ru.tayviscon.tayvisconbackend.team.support.TeamService;

@Slf4j
@Component
public class TeamMemberAuthenticationHandler {

    private final TeamService teamService;

    public TeamMemberAuthenticationHandler(TeamService teamService) {
        this.teamService = teamService;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        Authentication authentication = success.getAuthentication();
        if(authentication instanceof OAuth2LoginAuthenticationToken) {
            OAuth2LoginAuthenticationToken oauth = (OAuth2LoginAuthenticationToken) authentication;
            log.info("Успешная аутентификация OAuth для {}", oauth.getPrincipal().getAttribute("login").toString());
            if(oauth.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch("ROLE_ADMIN"::equals)) {
                Integer id = oauth.getPrincipal().getAttribute("id");
                log.info("Авторизованный пользователь {}", oauth.getPrincipal().getAttribute("login").toString());
                this.teamService.createOrUpdateMemberProfile(Integer.toUnsignedLong(id), oauth.getPrincipal());
            }
        }
    }
}
