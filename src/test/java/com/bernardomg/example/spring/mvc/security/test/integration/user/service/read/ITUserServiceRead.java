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

package com.bernardomg.example.spring.mvc.security.test.integration.user.service.read;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.example.spring.mvc.security.user.model.User;
import com.bernardomg.example.spring.mvc.security.user.service.UserService;
import com.google.common.collect.Iterables;

/**
 * Integration tests for the persistent user service, verifying that users can
 * be read.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@SpringJUnitConfig
@WebAppConfiguration
@ContextConfiguration(
        locations = { "classpath:context/application-test-context.xml" })
@Transactional
public class ITUserServiceRead {

    /**
     * User service being tested.
     */
    @Autowired
    @Qualifier("userService")
    private UserService service;

    /**
     * Default constructor.
     */
    public ITUserServiceRead() {
        super();
    }

    /**
     * Verifies that users can be read.
     */
    @Test
    @WithMockUser(username = "admin", authorities = { "READ_USER" })
    public final void testGetAllUsers() {
        final Iterable<? extends User> users; // Read users

        users = service.getAllUsers();

        Assertions.assertEquals(6, Iterables.size(users));
    }

    /**
     * Verifies that users and their privileges can be read.
     */
    @Test
    @WithMockUser(username = "admin", authorities = { "READ_USER" })
    public final void testGetAllUsers_Privileges() {
        final Iterable<? extends User> users; // Read users
        final User user; // Read user

        users = service.getAllUsers();

        // Finds the admin
        user = StreamSupport.stream(users.spliterator(), false)
                .filter((u) -> u.getUsername().equals("admin")).findFirst()
                .get();

        Assertions.assertEquals("admin", user.getUsername());
        Assertions.assertFalse(user.getRoles().isEmpty());
        Assertions.assertFalse(
                user.getRoles().iterator().next().getPrivileges().isEmpty());
    }

    /**
     * Verifies that a single user can be read.
     */
    @Test
    @WithMockUser(username = "admin", authorities = { "READ_USER" })
    public final void testGetUser_NoRoles() {
        final User user; // Read user

        user = service.getUser("noroles");

        Assertions.assertEquals("noroles", user.getUsername());
        Assertions.assertTrue(user.getRoles().isEmpty());
    }

    /**
     * Verifies that a single user can be read.
     */
    @Test
    @WithMockUser(username = "admin", authorities = { "READ_USER" })
    public final void testGetUser_Roles_Privileges() {
        final User user; // Read user

        user = service.getUser("admin");

        Assertions.assertEquals("admin", user.getUsername());
        Assertions.assertFalse(user.getRoles().isEmpty());
        Assertions.assertFalse(
                user.getRoles().iterator().next().getPrivileges().isEmpty());
    }

}
