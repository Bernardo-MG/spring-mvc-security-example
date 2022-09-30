/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017-2020 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.example.spring.mvc.security.auth.oauth;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.bernardomg.example.spring.mvc.security.domain.user.model.Privilege;
import com.bernardomg.example.spring.mvc.security.domain.user.model.Role;
import com.bernardomg.example.spring.mvc.security.domain.user.model.persistence.PersistentRole;
import com.bernardomg.example.spring.mvc.security.domain.user.model.persistence.PersistentUser;
import com.bernardomg.example.spring.mvc.security.domain.user.repository.PersistentRoleRepository;
import com.bernardomg.example.spring.mvc.security.domain.user.repository.PersistentUserRepository;

/**
 * OAUTH 2 user service linked to the DB users. It registers any new user based on his email.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class RegisterOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    /**
     * Logger.
     */
    private static final Logger            LOGGER   = LoggerFactory.getLogger(RegisterOAuth2UserService.class);

    /**
     * Base service. Applies inheritance through composition.
     */
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    /**
     * Roles repository.
     */
    private final PersistentRoleRepository roleRepository;

    /**
     * Users repository.
     */
    private final PersistentUserRepository userRepository;

    /**
     * Constructs a service.
     *
     * @param userRepo
     *            users repository
     * @param roleRepo
     *            roles repository
     */
    public RegisterOAuth2UserService(final PersistentUserRepository userRepo, final PersistentRoleRepository roleRepo) {
        super();

        userRepository = Objects.requireNonNull(userRepo, "Received a null pointer as users repository");
        roleRepository = Objects.requireNonNull(roleRepo, "Received a null pointer as users repository");
    }

    @Override
    public final OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final Map<String, Object>          attributes;
        final OAuth2AccessToken            accessToken;
        final Collection<GrantedAuthority> mappedAuthorities;
        final OAuth2User                   oauthuser;

        oauthuser = delegate.loadUser(userRequest);

        accessToken = userRequest.getAccessToken();
        mappedAuthorities = loadUser(oauthuser);

        LOGGER.debug("User {}", oauthuser);
        LOGGER.debug("Access token {}", accessToken);
        LOGGER.debug("Mapped authorities {}", mappedAuthorities);
        LOGGER.debug("Attributes {}", oauthuser.getAttributes());
        LOGGER.debug("Name {}", oauthuser.getName());

        attributes = oauthuser.getAttributes();

        return new DefaultOAuth2User(mappedAuthorities, attributes, "id");
    }

    /**
     * Loads the user and returns his authorities.
     * <p>
     * If the user exists on DB, the the authorities are taken from it. Otherwise, the user is created with default
     * authorities.
     *
     * @param oauthuser
     *            user to load
     * @return the user authorities
     */
    private final Collection<GrantedAuthority> loadUser(final OAuth2User oauthuser) {
        final Collection<GrantedAuthority> mappedAuthorities;
        final Optional<PersistentUser>     userOpt;
        final PersistentUser               user;
        final String                       email;
        final Collection<PersistentRole>   roles;
        final Collection<String>           privileges;

        // TODO: What if somebody creates a fake account with the email? Is it
        // possible?
        if (oauthuser.getAttributes()
            .containsKey("email")) {
            email = String.valueOf(oauthuser.getAttributes()
                .get("email"));
            userOpt = userRepository.findOneByEmail(email);
            if (userOpt.isPresent()) {
                LOGGER.trace("Found user for email {}", email);
                user = userOpt.get();
            } else {
                LOGGER.debug("No user found for email {}. Creating new user", email);
                user = new PersistentUser();
                if (oauthuser.getAttributes()
                    .containsKey("name")) {
                    user.setUsername(oauthuser.getAttributes()
                        .get("name")
                        .toString());
                } else {
                    LOGGER.warn("OAUTH user {} is missing name field, applying email as name", oauthuser.getName());
                    user.setUsername(email);
                }
                user.setEmail(email);

                roles = roleRepository.findByNameIn(Arrays.asList("USER"));

                user.setRoles(roles);

                userRepository.save(user);
            }

            privileges = StreamSupport.stream(user.getRoles()
                .spliterator(), false)
                .map(Role::getPrivileges)
                .flatMap(p -> StreamSupport.stream(p.spliterator(), false))
                .map(Privilege::getName)
                .collect(Collectors.toList());
            mappedAuthorities = AuthorityUtils.createAuthorityList(privileges.toArray(new String[] {}));
        } else {
            LOGGER.warn("OAUTH user {} is missing email attribute", oauthuser.getName());
            mappedAuthorities = Collections.emptyList();
        }

        return mappedAuthorities;
    }

}
