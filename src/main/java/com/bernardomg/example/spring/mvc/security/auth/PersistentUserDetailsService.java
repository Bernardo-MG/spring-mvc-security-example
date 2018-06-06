/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017-2018 the original author or authors.
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

package com.bernardomg.example.spring.mvc.security.auth;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bernardomg.example.spring.mvc.security.persistence.model.PersistentUser;
import com.bernardomg.example.spring.mvc.security.persistence.repository.PersistentUserDetailsRepository;

/**
 * User details service which takes the data from the persistence layer.
 * <p>
 * It uses a Spring repository and searches for any user detail matching the
 * received username.
 * <p>
 * This search is case insensitive, as the persisted user details are expected
 * to contain the username in lower case.
 * 
 * @author Bernardo
 *
 */
@Service("userDetailsService")
public final class PersistentUserDetailsService implements UserDetailsService {

    /**
     * Logger.
     */
    private static final Logger                   LOGGER = LoggerFactory
            .getLogger(PersistentUserDetailsService.class);

    /**
     * Repository for the user data.
     */
    private final PersistentUserDetailsRepository userRepo;

    /**
     * Constructs a user details service.
     * 
     * @param userRepository
     *            repository for user details
     */
    @Autowired
    public PersistentUserDetailsService(
            final PersistentUserDetailsRepository userRepository) {
        super();

        userRepo = checkNotNull(userRepository,
                "Received a null pointer as repository");
    }

    @Override
    public final UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        final Optional<PersistentUser> user;
        final UserDetails details;

        LOGGER.debug("Asked for username {}", username);

        user = getPersistentUserDetailsRepository()
                .findOneByUsername(username.toLowerCase());

        if (user.isPresent()) {
            LOGGER.debug("Username {} found in DB", username);
            details = toUserDetails(user.get());
        } else {
            LOGGER.debug("Username {} not found in DB", username);
            throw new UsernameNotFoundException(username);
        }

        return details;
    }

    /**
     * Returns the user details repository.
     * 
     * @return the user details repository
     */
    private final PersistentUserDetailsRepository
            getPersistentUserDetailsRepository() {
        return userRepo;
    }

    /**
     * Transforms a user entity into a user details object.
     * 
     * @param user
     *            entity to transform
     * @return equivalent user details
     */
    private final UserDetails toUserDetails(final PersistentUser user) {
        final UserDetails details;
        final Boolean enabled;
        final Boolean accountNonExpired;
        final Boolean credentialsNonExpired;
        final Boolean accountNonLocked;
        final Collection<? extends GrantedAuthority> authorities;

        enabled = user.getEnabled();
        accountNonExpired = !user.getExpired();
        credentialsNonExpired = !user.getExpired();
        accountNonLocked = !user.getLocked();

        authorities = user.getRoles().stream()
                .map((r) -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());

        details = new User(user.getUsername(), user.getPassword(), enabled,
                accountNonExpired, credentialsNonExpired, accountNonLocked,
                authorities);

        return details;
    }

}
