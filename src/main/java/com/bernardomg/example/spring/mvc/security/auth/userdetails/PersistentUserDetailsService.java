/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.example.spring.mvc.security.auth.userdetails;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bernardomg.example.spring.mvc.security.auth.user.model.Privilege;
import com.bernardomg.example.spring.mvc.security.auth.user.repository.PrivilegeRepository;
import com.bernardomg.example.spring.mvc.security.auth.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * User details service which takes the user data from the persistence layer.
 * <p>
 * Makes use of repositories, which will return the user and his privileges.
 * <p>
 * The user search is based on the username, and is case insensitive. As the persisted user details are expected to
 * contain the username in lower case.
 * <h2>Granted authorities</h2>
 * <p>
 * Privileges are read moving through the model. The service receives a username and then finds the privileges assigned
 * to the related user:
 * <p>
 * {@code user -> role -> privileges}
 * <p>
 * These privileges are used to create the granted authorities.
 * <h2>Exceptions</h2>
 * <p>
 * When loading users any of these cases throws a {@code UsernameNotFoundException}:
 * <ul>
 * <li>There is no user for the username</li>
 * <li>Theres is a user, but he has no privileges</li>
 * </ul>
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class PersistentUserDetailsService implements UserDetailsService {

    /**
     * Repository for the privileges.
     */
    private final PrivilegeRepository privilegeRepo;

    /**
     * Repository for the user data.
     */
    private final UserRepository      userRepo;

    /**
     * Constructs a user details service.
     *
     * @param userRepository
     *            repository for user details
     * @param privilegeRepository
     *            repository for privileges
     */
    public PersistentUserDetailsService(final UserRepository userRepository,
            final PrivilegeRepository privilegeRepository) {
        super();

        userRepo = Objects.requireNonNull(userRepository, "Received a null pointer as repository");
        privilegeRepo = Objects.requireNonNull(privilegeRepository, "Received a null pointer as repository");
    }

    @Override
    public final UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<com.bernardomg.example.spring.mvc.security.auth.user.model.User> user;
        final Collection<? extends GrantedAuthority>                                    authorities;
        final UserDetails                                                               details;

        user = userRepo.findOneByUsername(username.toLowerCase());

        if (!user.isPresent()) {
            log.warn("Username {} not found in DB", username);
            throw new UsernameNotFoundException(username);
        }

        authorities = getAuthorities(user.get()
            .getId());

        if (authorities.isEmpty()) {
            log.warn("Username {} has no authorities", username);
            throw new UsernameNotFoundException(username);
        }

        details = toUserDetails(user.get(), authorities);

        log.debug("User {} exists", username);
        log.debug("Authorities for {}: {}", username, details.getAuthorities());
        log.debug("User {} is enabled: {}", username, details.isEnabled());
        log.debug("User {} is non expired: {}", username, details.isAccountNonExpired());
        log.debug("User {} is non locked: {}", username, details.isAccountNonLocked());
        log.debug("User {} has credentials non expired: {}", username, details.isCredentialsNonExpired());

        return details;
    }

    /**
     * Returns all the authorities for the user.
     *
     * @param id
     *            id of the user
     * @return all the authorities for the user
     */
    private final Collection<GrantedAuthority> getAuthorities(final Long id) {
        return privilegeRepo.findForUser(id)
            .stream()
            .map(Privilege::getName)
            .distinct()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    /**
     * Transforms a user entity into a user details object.
     *
     * @param user
     *            entity to transform
     * @return equivalent user details
     */
    private final UserDetails toUserDetails(final com.bernardomg.example.spring.mvc.security.auth.user.model.User user,
            final Collection<? extends GrantedAuthority> authorities) {
        final Boolean enabled;
        final Boolean accountNonExpired;
        final Boolean credentialsNonExpired;
        final Boolean accountNonLocked;

        // Loads status
        enabled = user.getEnabled();
        accountNonExpired = !user.getExpired();
        credentialsNonExpired = !user.getCredentialsExpired();
        accountNonLocked = !user.getLocked();

        return new User(user.getUsername(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
            accountNonLocked, authorities);
    }

}
