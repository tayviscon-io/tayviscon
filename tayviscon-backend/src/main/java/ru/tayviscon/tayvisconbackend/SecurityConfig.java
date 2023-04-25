package ru.tayviscon.tayvisconbackend;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OAuth2UserService<OAuth2UserRequest, OAuth2User> userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(githubBasicAuthFilter(), BasicAuthenticationFilter.class)
                .exceptionHandling(handling -> handling.authenticationEntryPoint(entryPoint()))
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/webhook/**"))
                .requiresChannel(channel ->
                        channel.requestMatchers(request -> request.getHeader("x-forwarded-port") != null).requiresSecure())
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                )
                .oauth2Login(login -> login
                        .defaultSuccessUrl("/admin").loginPage("/signin")
                        .userInfoEndpoint(endpoint -> endpoint.userService(this.userService)))
                .logout(logout -> logout.logoutUrl("/signout").logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

    private Filter githubBasicAuthFilter() {
        return new BasicAuthenticationFilter(this.authenticationManager);
    }

    private AuthenticationEntryPoint entryPoint() {
        LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> mapping = new LinkedHashMap<>();
        mapping.put(new AntPathRequestMatcher("/api/**"), new Http403ForbiddenEntryPoint());
        DelegatingAuthenticationEntryPoint result = new DelegatingAuthenticationEntryPoint(mapping);
        result.setDefaultEntryPoint(new LoginUrlAuthenticationEntryPoint("/signin"));
        return result;
    }

}
