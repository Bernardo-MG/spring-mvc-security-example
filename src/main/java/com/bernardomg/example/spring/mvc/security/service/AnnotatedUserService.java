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

package com.bernardomg.example.spring.mvc.security.service;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bernardomg.example.spring.mvc.security.model.UserForm;
import com.bernardomg.example.spring.mvc.security.persistence.model.PersistentUserDetails;
import com.bernardomg.example.spring.mvc.security.persistence.repository.PersistentUserDetailsRepository;

/**
 * Annotation-based users service.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service("UserService")
public class AnnotatedUserService implements UserService {

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
     */
    @Autowired
    public AnnotatedUserService(final PersistentUserDetailsRepository userRepo,
            final PasswordEncoder passEncoder) {
        super();

        repository = checkNotNull(userRepo,
                "Received a null pointer as users repository");
        passwordEncoder = checkNotNull(passEncoder,
                "Received a null pointer as password encoder");
    }

    @Override
    public Iterable<? extends UserDetails> getAllUsers() {
        return getPersistentUserDetailsRepository().findAll();
    }

    @Override
    public final void save(final UserForm user) {
        final PersistentUserDetails entity;
        final String encodedPassword;

        entity = new PersistentUserDetails();
        entity.setUsername(user.getUsername());
        entity.setEnabled(user.getEnabled());
        entity.setExpired(user.getExpired());
        entity.setLocked(user.getLocked());

        // Password is encoded
        encodedPassword = getPasswordEncoder().encode(user.getPassword());
        entity.setPassword(encodedPassword);

        getPersistentUserDetailsRepository().save(entity);
    }

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
