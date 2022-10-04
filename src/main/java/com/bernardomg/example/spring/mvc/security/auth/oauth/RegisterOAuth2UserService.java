/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017-2022 the original author or authors.
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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.bernardomg.example.spring.mvc.security.auth.user.model.PersistentRole;
import com.bernardomg.example.spring.mvc.security.auth.user.model.PersistentUser;
import com.bernardomg.example.spring.mvc.security.auth.user.model.Privilege;
import com.bernardomg.example.spring.mvc.security.auth.user.repository.PrivilegeRepository;
import com.bernardomg.example.spring.mvc.security.auth.user.repository.RoleRepository;
import com.bernardomg.example.spring.mvc.security.auth.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * OAUTH 2 user service linked to the DB users. It registers any new user based on his email.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class RegisterOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    /**
     * Base service. Applies inheritance through composition.
     */
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    /**
     * Repository for the privileges.
     */
    private final PrivilegeRepository      privilegeRepository;

    /**
     * Roles repository.
     */
    private final RoleRepository           roleRepository;

    /**
     * Users repository.
     */
    private final UserRepository           userRepository;

    /**
     * Constructs a service.
     *
     * @param userRepo
     *            users repository
     * @param roleRepo
     *            roles repository
     */
    public RegisterOAuth2UserService(final UserRepository userRepo, final RoleRepository roleRepo,
            final PrivilegeRepository privilegeRepo) {
        super();

        userRepository = Objects.requireNonNull(userRepo, "Received a null pointer as users repository");
        roleRepository = Objects.requireNonNull(roleRepo, "Received a null pointer as roles repository");
        privilegeRepository = Objects.requireNonNull(privilegeRepo, "Received a null pointer as privileges repository");
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

        log.debug("User {}", oauthuser);
        log.debug("Access token {}", accessToken);
        log.debug("Mapped authorities {}", mappedAuthorities);
        log.debug("Attributes {}", oauthuser.getAttributes());
        log.debug("Name {}", oauthuser.getName());

        attributes = oauthuser.getAttributes();

        return new DefaultOAuth2User(mappedAuthorities, attributes, "id");
    }

    /**
     * Returns all the authorities for the user.
     *
     * @param id
     *            id of the user
     * @return all the authorities for the user
     */
    private final Collection<GrantedAuthority> getAuthorities(final Long id) {
        return privilegeRepository.findForUser(id)
            .stream()
            .map(Privilege::getName)
            .distinct()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
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
        final Collection<GrantedAuthority> authorities;
        final Optional<PersistentUser>     userOpt;
        final PersistentUser               user;
        final String                       email;
        final Optional<PersistentRole>     role;

        // TODO: What if somebody creates a fake account with the email? Is it
        // possible?
        if (oauthuser.getAttributes()
            .containsKey("email")) {
            email = String.valueOf(oauthuser.getAttributes()
                .get("email"));
            userOpt = userRepository.findOneByEmail(email);
            if (userOpt.isPresent()) {
                log.trace("Found user for email {}", email);
                user = userOpt.get();
            } else {
                log.debug("No user found for email {}. Creating new user", email);
                user = new PersistentUser();
                if (oauthuser.getAttributes()
                    .containsKey("name")) {
                    user.setUsername(oauthuser.getAttributes()
                        .get("name")
                        .toString());
                } else {
                    log.warn("OAUTH user {} is missing name field, applying email as name", oauthuser.getName());
                    user.setUsername(email);
                }
                user.setEmail(email);

                userRepository.save(user);

                role = roleRepository.findByName("USER");
                if (role.isPresent()) {
                    roleRepository.registerForUser(user.getId(), role.get()
                        .getId());
                }
            }

            authorities = getAuthorities(user.getId());
        } else {
            log.warn("OAUTH user {} is missing email attribute", oauthuser.getName());
            authorities = Collections.emptyList();
        }

        return authorities;
    }

}
