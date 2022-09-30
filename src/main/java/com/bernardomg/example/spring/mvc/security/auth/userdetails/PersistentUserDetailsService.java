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

package com.bernardomg.example.spring.mvc.security.auth.userdetails;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bernardomg.example.spring.mvc.security.domain.user.model.Privilege;
import com.bernardomg.example.spring.mvc.security.domain.user.model.Role;
import com.bernardomg.example.spring.mvc.security.domain.user.model.persistence.PersistentUser;
import com.bernardomg.example.spring.mvc.security.domain.user.repository.PersistentUserRepository;

/**
 * User details service which takes the data from the persistence layer.
 * <p>
 * It uses a Spring repository and searches for any user detail matching the received username.
 * <p>
 * This search is case insensitive, as the persisted user details are expected to contain the username in lower case.
 *
 * @author Bernardo
 *
 */
public final class PersistentUserDetailsService implements UserDetailsService {

    /**
     * Logger.
     */
    private static final Logger            LOGGER = LoggerFactory.getLogger(PersistentUserDetailsService.class);

    /**
     * Repository for the user data.
     */
    private final PersistentUserRepository userRepo;

    /**
     * Constructs a user details service.
     *
     * @param userRepository
     *            repository for user details
     */
    public PersistentUserDetailsService(final PersistentUserRepository userRepository) {
        super();

        userRepo = Objects.requireNonNull(userRepository, "Received a null pointer as repository");
    }

    @Override
    public final UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<PersistentUser> user;

        LOGGER.debug("Asked for username {}", username);

        user = userRepo.findOneByUsername(username.toLowerCase());

        if (!user.isPresent()) {
            LOGGER.debug("Username {} not found in DB", username);
            throw new UsernameNotFoundException(username);
        }
        LOGGER.debug("Username {} found in DB", username);
        return toUserDetails(user.get());
    }

    /**
     * Transforms a user entity into a user details object.
     *
     * @param user
     *            entity to transform
     * @return equivalent user details
     */
    private final UserDetails toUserDetails(final PersistentUser user) {
        final Boolean                                enabled;
        final Boolean                                accountNonExpired;
        final Boolean                                credentialsNonExpired;
        final Boolean                                accountNonLocked;
        final Collection<? extends GrantedAuthority> authorities;
        final Collection<? extends Privilege>        privileges;

        // Loads status
        enabled = user.getEnabled();
        accountNonExpired = !user.getExpired();
        credentialsNonExpired = !user.getCredentialsExpired();
        accountNonLocked = !user.getLocked();

        // Loads privileges
        privileges = StreamSupport.stream(user.getRoles()
            .spliterator(), false)
            .map(Role::getPrivileges)
            .flatMap(p -> StreamSupport.stream(p.spliterator(), false))
            .collect(Collectors.toList());
        LOGGER.trace("Privileges for {}: {}", user.getUsername(), privileges);

        // Loads authorities
        authorities = privileges.stream()
            .map(Privilege::getName)
            .distinct()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        LOGGER.debug("Authorities for {}: {}", user.getUsername(), authorities);

        return new User(user.getUsername(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
            accountNonLocked, authorities);
    }

}
