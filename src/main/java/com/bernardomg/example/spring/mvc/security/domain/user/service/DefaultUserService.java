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

package com.bernardomg.example.spring.mvc.security.domain.user.service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bernardomg.example.spring.mvc.security.domain.user.model.Role;
import com.bernardomg.example.spring.mvc.security.domain.user.model.User;
import com.bernardomg.example.spring.mvc.security.domain.user.model.form.UserForm;
import com.bernardomg.example.spring.mvc.security.domain.user.model.form.UserRolesForm;
import com.bernardomg.example.spring.mvc.security.domain.user.model.persistence.PersistentRole;
import com.bernardomg.example.spring.mvc.security.domain.user.model.persistence.PersistentUser;
import com.bernardomg.example.spring.mvc.security.domain.user.repository.PersistentRoleRepository;
import com.bernardomg.example.spring.mvc.security.domain.user.repository.PersistentUserRepository;

import lombok.AllArgsConstructor;

/**
 * Users service based on {@link PersistentUser} and Spring classes.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
public final class DefaultUserService implements UserService {

    /**
     * Logger.
     */
    private static final Logger            LOGGER = LoggerFactory.getLogger(DefaultUserService.class);

    /**
     * Password encoder.
     */
    private final PasswordEncoder          passwordEncoder;

    /**
     * Users repository.
     */
    private final PersistentRoleRepository roleRepository;

    /**
     * Users repository.
     */
    private final PersistentUserRepository userRepository;

    @Override
    public final void create(final UserForm user) {
        final PersistentUser entity;
        final String         encodedPassword;

        Objects.requireNonNull(user);

        entity = new PersistentUser();

        entity.setUsername(user.getUsername());

        // TODO: This flag should come from the frontend
        entity.setCredentialsExpired(false);
        entity.setEnabled(user.getEnabled());
        entity.setExpired(user.getExpired());
        entity.setLocked(user.getLocked());

        if (user.getPassword() == null) {
            // Let the persistence layer handle this error
            encodedPassword = null;
        } else {
            // Password is encoded
            encodedPassword = passwordEncoder.encode(user.getPassword());
        }
        entity.setPassword(encodedPassword);

        userRepository.save(entity);
    }

    @Override
    @Cacheable("roles")
    public final Iterable<? extends Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Cacheable("users")
    public final Iterable<? extends User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Cacheable("user")
    public final User getUser(final String username) {
        final Optional<PersistentUser> read;
        final User                     user;

        Objects.requireNonNull(username);

        read = userRepository.findOneByUsername(username);

        if (read.isPresent()) {
            user = read.get();
        } else {
            // TODO: Throw an exception maybe?
            LOGGER.warn("User {} not found", username);
            user = null;
        }

        return user;
    }

    @Override
    public final void update(final UserForm user) {
        final PersistentUser entity;
        final String         encodedPassword;

        Objects.requireNonNull(user);

        entity = userRepository.findOneByUsername(user.getUsername())
            .get();

        BeanUtils.copyProperties(user, entity);

        if (user.getPassword() == null) {
            // Let the persistence layer handle this error
            encodedPassword = null;
        } else {
            // Password is encoded
            encodedPassword = passwordEncoder.encode(user.getPassword());
        }
        entity.setPassword(encodedPassword);

        userRepository.save(entity);
    }

    @Override
    public final void updateRoles(final UserRolesForm userRoles) {
        final Collection<PersistentRole> roles;
        final Optional<PersistentUser>   read;
        final PersistentUser             user;

        Objects.requireNonNull(userRoles);

        read = userRepository.findOneByUsername(userRoles.getUsername());

        if (read.isPresent()) {
            user = read.get();

            roles = roleRepository.findByNameIn(userRoles.getRoles());

            user.setRoles(roles);

            userRepository.save(user);
        } else {
            LOGGER.warn("User {} not found", userRoles.getUsername());
        }
    }

}
