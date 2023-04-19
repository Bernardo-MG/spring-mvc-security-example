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

package com.bernardomg.example.spring.security.mvc.test.security.userdetails.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.example.spring.security.mvc.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Persistent user details service user reading tests")
public class ITPersistentUserDetailsService {

    /**
     * User service being tested.
     */
    @Autowired
    private UserDetailsService service;

    /**
     * Default constructor.
     */
    public ITPersistentUserDetailsService() {
        super();
        // TODO: These tests can be done by mocking the repository
    }

    @Test
    @DisplayName("It is possible to read a user with authorities")
    @Sql({ "/db/queries/user/single.sql", "/db/queries/security/default_role.sql" })
    public final void testGetUser_Authorities() {
        final UserDetails user;

        user = service.loadUserByUsername("admin");

        Assertions.assertEquals("admin", user.getUsername());
        Assertions.assertFalse(user.getAuthorities()
            .isEmpty());
    }

    @Test
    @DisplayName("It is possible to read a disabled user")
    @Sql({ "/db/queries/user/disabled.sql", "/db/queries/security/default_role.sql" })
    public final void testGetUser_Disabled() {
        final UserDetails user;

        user = service.loadUserByUsername("admin");

        Assertions.assertEquals("admin", user.getUsername());
    }

    @Test
    @DisplayName("It is not possible to read a user with no authorities")
    @Sql({ "/db/queries/user/single.sql" })
    public final void testGetUser_NoAuthorities() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("admin"));
    }

    @Test
    @DisplayName("Reading a not existing user throws an exception")
    public final void testGetUser_NotExisting_Exception() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("admin"));
    }

}
