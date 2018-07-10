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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bernardomg.example.spring.mvc.security.model.UserForm;
import com.bernardomg.example.spring.mvc.security.user.model.User;
import com.bernardomg.example.spring.mvc.security.user.model.persistence.PersistentUser;
import com.bernardomg.example.spring.mvc.security.user.repository.PersistentUserDetailsRepository;

/**
 * Users service based on {@link PersistentUser} and Spring classes.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service("userService")
public final class SpringUserService implements UserService {

    /**
     * Password encoder.
     */
    private final PasswordEncoder                 passwordEncoder;

    /**
     * Users repository.
     */
    private final PersistentUserDetailsRepository repository;

    /**
     * Default constructor.
     * 
     * @param userRepo
     *            users repository
     * @param passEncoder
     *            password encoder
     */
    @Autowired
    public SpringUserService(final PersistentUserDetailsRepository userRepo,
            final PasswordEncoder passEncoder) {
        super();

        repository = checkNotNull(userRepo,
                "Received a null pointer as users repository");
        passwordEncoder = checkNotNull(passEncoder,
                "Received a null pointer as password encoder");
    }

    @Override
    public final void create(final UserForm user) {
        final PersistentUser entity;
        final String encodedPassword;

        entity = new PersistentUser();

        entity.setUsername(user.getUsername());

        // TODO: This flag should come from the frontend
        entity.setCredentialsExpired(false);
        entity.setEnabled(user.getEnabled());
        entity.setExpired(user.getExpired());
        entity.setLocked(user.getLocked());

        if (user.getPassword() == null) {
            // Let the persistence layer handle this
            encodedPassword = null;
        } else {
            // Password is encoded
            encodedPassword = getPasswordEncoder().encode(user.getPassword());
        }
        entity.setPassword(encodedPassword);

        getPersistentUserDetailsRepository().save(entity);
    }

    @Override
    public final Iterable<? extends User> getAllUsers() {
        return getPersistentUserDetailsRepository().findAll();
    }

    @Override
    public final User getUser(final String username) {
        final Optional<PersistentUser> read;
        final User user;

        read = getPersistentUserDetailsRepository().findOneByUsername(username);

        if (read.isPresent()) {
            user = read.get();
        } else {
            // TODO: Throw an exception maybe?
            user = null;
        }

        return user;
    }

    @Override
    public final void update(final UserForm user) {
        final PersistentUser entity;
        final String encodedPassword;

        entity = getPersistentUserDetailsRepository()
                .findOneByUsername(user.getUsername()).get();

        BeanUtils.copyProperties(user, entity);

        if (user.getPassword() == null) {
            // Let the persistence layer handle this
            encodedPassword = null;
        } else {
            // Password is encoded
            encodedPassword = getPasswordEncoder().encode(user.getPassword());
        }
        entity.setPassword(encodedPassword);

        getPersistentUserDetailsRepository().save(entity);
    }

    /**
     * Returns the password encoder.
     * 
     * @return the password encoder
     */
    private final PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * Returns the users service.
     * 
     * @return the users service
     */
    private final PersistentUserDetailsRepository
            getPersistentUserDetailsRepository() {
        return repository;
    }

}
