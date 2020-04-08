
package com.bernardomg.example.spring.mvc.security.auth.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.bernardomg.example.spring.mvc.security.user.model.Privilege;
import com.bernardomg.example.spring.mvc.security.user.model.Role;
import com.bernardomg.example.spring.mvc.security.user.model.persistence.PersistentRole;
import com.bernardomg.example.spring.mvc.security.user.model.persistence.PersistentUser;
import com.bernardomg.example.spring.mvc.security.user.repository.PersistentRoleRepository;
import com.bernardomg.example.spring.mvc.security.user.repository.PersistentUserRepository;

public final class CustomOAuth2UserService
        implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    /**
     * Logger.
     */
    private static final Logger            LOGGER   = LoggerFactory
            .getLogger(CustomOAuth2UserService.class);

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    private final PersistentRoleRepository roleRepository;

    private final PersistentUserRepository userRepository;

    public CustomOAuth2UserService(final PersistentUserRepository userRepo,
            final PersistentRoleRepository roleRepo) {
        super();

        userRepository = checkNotNull(userRepo,
                "Received a null pointer as users repository");
        roleRepository = checkNotNull(roleRepo,
                "Received a null pointer as users repository");
    }

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        final Map<String, Object> attributes;
        final Set<GrantedAuthority> authorities;
        final OAuth2AccessToken accessToken;
        final Collection<GrantedAuthority> mappedAuthorities;
        final OAuth2User oauthuser;
        final Optional<PersistentUser> userOpt;
        final PersistentUser user;
        final String email;
        final Iterable<PersistentRole> roles;
        final Collection<String> privileges;

        oauthuser = delegate.loadUser(userRequest);

        accessToken = userRequest.getAccessToken();
        if (oauthuser.getAttributes().containsKey("email")) {
            email = oauthuser.getAttributes().get("email").toString();
            userOpt = userRepository.findOneByEmail(email);
            if (userOpt.isPresent()) {
                user = userOpt.get();
            } else {
                user = new PersistentUser();
                if (oauthuser.getAttributes().containsKey("name")) {
                    user.setUsername(
                            oauthuser.getAttributes().get("name").toString());
                }
                user.setEmail(email);

                roles = roleRepository.findByNameIn(Arrays.asList("USER"));

                user.setRoles(roles);

                userRepository.save(user);
            }

            privileges = user.getRoles().stream().map(Role::getPrivileges)
                    .flatMap(Collection::stream).map(Privilege::getName)
                    .collect(Collectors.toList());
            mappedAuthorities = AuthorityUtils
                    .createAuthorityList(privileges.toArray(new String[] {}));
        } else {
            LOGGER.warn("OAUTH user {} is missing email attribute",
                    oauthuser.getName());
            mappedAuthorities = Collections.emptyList();
        }

        LOGGER.debug("User {}", oauthuser);
        LOGGER.debug("Mapped authorities {}", mappedAuthorities);
        LOGGER.debug("Attributes {}", oauthuser.getAttributes());
        LOGGER.debug("Name {}", oauthuser.getName());

        attributes = oauthuser.getAttributes();
        authorities = new HashSet<>(oauthuser.getAuthorities());

        authorities.add(new SimpleGrantedAuthority("READ_USER"));

        return new DefaultOAuth2User(mappedAuthorities, attributes, "id");
    }

}
