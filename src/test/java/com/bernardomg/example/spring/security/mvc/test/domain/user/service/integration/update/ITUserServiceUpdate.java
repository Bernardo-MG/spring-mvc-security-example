/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017-2023 the original author or authors.
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

package com.bernardomg.example.spring.security.mvc.test.domain.user.service.integration.update;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.example.spring.security.mvc.domain.user.model.form.DefaultUserForm;
import com.bernardomg.example.spring.security.mvc.domain.user.service.UserService;
import com.bernardomg.example.spring.security.mvc.security.user.model.PersistentUser;
import com.bernardomg.example.spring.security.mvc.security.user.repository.UserRepository;
import com.bernardomg.example.spring.security.mvc.test.configuration.annotation.IntegrationTest;

/**
 * Integration tests for the persistent user service, verifying that users can be updated.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@IntegrationTest
@Sql({ "/db/queries/user/single.sql", "/db/queries/security/default_role.sql" })
@DisplayName("User service update operations")
public class ITUserServiceUpdate {

    /**
     * User repository.
     */
    @Autowired
    private UserRepository repository;

    /**
     * User service being tested.
     */
    @Autowired
    private UserService    service;

    /**
     * Default constructor.
     */
    public ITUserServiceUpdate() {
        super();
    }

    @Test
    @WithMockUser(username = "test", authorities = { "UPDATE_DATA" })
    @DisplayName("An authenticated user can update users")
    public final void testUpdate() {
        final DefaultUserForm user;    // User to save
        final PersistentUser  updated; // Updated user

        user = new DefaultUserForm();
        user.setUsername("admin");
        user.setPassword("password");
        user.setEnabled(false);

        service.update(user);

        updated = repository.findOneByUsername("admin")
            .get();

        Assertions.assertEquals(false, updated.getEnabled());
    }

}
