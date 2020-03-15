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

package com.bernardomg.example.spring.mvc.security.user.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.example.spring.mvc.security.user.model.Role;
import com.bernardomg.example.spring.mvc.security.user.model.User;
import com.bernardomg.example.spring.mvc.security.user.model.form.UserForm;
import com.bernardomg.example.spring.mvc.security.user.model.form.UserRolesForm;
import com.bernardomg.example.spring.mvc.security.user.model.persistence.PersistentRole;
import com.bernardomg.example.spring.mvc.security.user.model.persistence.PersistentUser;
import com.bernardomg.example.spring.mvc.security.user.repository.PersistentRoleRepository;
import com.bernardomg.example.spring.mvc.security.user.repository.PersistentUserRepository;

/**
 * Users service based on {@link PersistentUser} and Spring classes.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class SpringUserService implements UserService {

    /**
     * Logger.
     */
    private static final Logger            LOGGER = LoggerFactory
            .getLogger(SpringUserService.class);

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

    /**
     * Default constructor.
     * 
     * @param userRepo
     *            users repository
     * @param roleRepo
     *            roles repository
     * @param passEncoder
     *            password encoder
     */
    public SpringUserService(final PersistentUserRepository userRepo,
            final PersistentRoleRepository roleRepo,
            final PasswordEncoder passEncoder) {
        super();

        userRepository = checkNotNull(userRepo,
                "Received a null pointer as users repository");
        roleRepository = checkNotNull(roleRepo,
                "Received a null pointer as roles repository");
        passwordEncoder = checkNotNull(passEncoder,
                "Received a null pointer as password encoder");
    }

    @Override
    public final void create(final UserForm user) {
        final PersistentUser entity;
        final String encodedPassword;

        checkNotNull(user);

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
    public final Iterable<? extends Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public final Iterable<? extends User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public final User getUser(final String username) {
        final Optional<PersistentUser> read;
        final User user;

        checkNotNull(username);

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
        final String encodedPassword;

        checkNotNull(user);

        entity = userRepository.findOneByUsername(user.getUsername()).get();

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
        final Iterable<PersistentRole> roles;
        final Optional<PersistentUser> read;
        final PersistentUser user;

        checkNotNull(userRoles);

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
