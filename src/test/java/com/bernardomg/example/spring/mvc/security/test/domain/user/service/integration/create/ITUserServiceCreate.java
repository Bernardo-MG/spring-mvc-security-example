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

package com.bernardomg.example.spring.mvc.security.test.domain.user.service.integration.create;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.example.spring.mvc.security.domain.user.model.form.DefaultUserForm;
import com.bernardomg.example.spring.mvc.security.domain.user.model.persistence.PersistentUser;
import com.bernardomg.example.spring.mvc.security.domain.user.repository.PersistentUserRepository;
import com.bernardomg.example.spring.mvc.security.domain.user.service.UserService;
import com.bernardomg.example.spring.mvc.security.test.configuration.annotation.IntegrationTest;

/**
 * Integration tests for the persistent user service, verifying that users can be created.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@IntegrationTest
@Sql("/db/populate/full.sql")
@DisplayName("User service creation operations")
public class ITUserServiceCreate {

    /**
     * User repository.
     */
    @Autowired
    private PersistentUserRepository repository;

    /**
     * User service being tested.
     */
    @Autowired
    private UserService              service;

    /**
     * Default constructor.
     */
    public ITUserServiceCreate() {
        super();
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "CREATE_USER" })
    @DisplayName("An authenticated user can create other users")
    public final void testCreate() {
        final DefaultUserForm          user;  // User to save
        final Iterable<PersistentUser> users; // Read users

        user = new DefaultUserForm();
        user.setUsername("username");
        user.setPassword("password");

        service.create(user);

        users = repository.findAll();

        Assertions.assertEquals(7, IterableUtils.size(users));
    }

}
