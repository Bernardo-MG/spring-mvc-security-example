
package com.bernardomg.example.spring.mvc.security.auth.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public final class CustomOAuth2UserService
        implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    /**
     * Logger.
     */
    private static final Logger            LOGGER   = LoggerFactory
            .getLogger(CustomOAuth2UserService.class);

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    public CustomOAuth2UserService() {
        super();
    }

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        final Map<String, Object> attributes;
        final Set<GrantedAuthority> authorities;
        final OAuth2AccessToken accessToken;
        final Set<GrantedAuthority> mappedAuthorities;
        final OAuth2User user;

        user = delegate.loadUser(userRequest);

        accessToken = userRequest.getAccessToken();
        mappedAuthorities = new HashSet<>();

        mappedAuthorities
                .addAll(AuthorityUtils.createAuthorityList("READ_USER"));

        LOGGER.debug("User {}", user);
        LOGGER.debug("Mapped authorities {}", mappedAuthorities);
        LOGGER.debug("Attributes {}", user.getAttributes());
        LOGGER.debug("Name {}", user.getName());

        attributes = user.getAttributes();
        authorities = new HashSet<>(user.getAuthorities());

        authorities.add(new SimpleGrantedAuthority("READ_USER"));

        return new DefaultOAuth2User(mappedAuthorities, attributes, "id");
    }

}
