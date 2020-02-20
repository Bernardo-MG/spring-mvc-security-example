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

package com.bernardomg.example.spring.mvc.security.test.integration.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.example.spring.mvc.security.auth.PersistentUserDetailsService;

/**
 * Integration tests for the persistent user details service, verifying that
 * users can be read.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@ExtendWith(SpringExtension.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class })
@WebAppConfiguration
@ContextConfiguration(
        locations = { "classpath:context/application-context.xml" })
@Transactional
public class ITPersistentUserDetailsService {

    /**
     * User service being tested.
     */
    @Autowired
    private PersistentUserDetailsService service;

    /**
     * Default constructor.
     */
    public ITPersistentUserDetailsService() {
        super();
        // TODO: These tests can be done by mocking the repository
    }

    /**
     * Verifies that a single user with authorities can be read.
     */
    @Test
    public final void testGetUser_Authorities() {
        final UserDetails user; // Read user

        user = service.loadUserByUsername("admin");

        Assertions.assertEquals("admin", user.getUsername());
        Assertions.assertFalse(user.getAuthorities().isEmpty());
    }

    /**
     * Verifies that a disabled user can be read.
     */
    @Test
    public final void testGetUser_Disabled() {
        final UserDetails user; // Read user

        user = service.loadUserByUsername("disabled");

        Assertions.assertEquals("disabled", user.getUsername());
    }

    /**
     * Verifies that a single user with no authorities can be read.
     */
    @Test
    public final void testGetUser_NoAuthorities() {
        final UserDetails user; // Read user

        user = service.loadUserByUsername("noroles");

        Assertions.assertEquals("noroles", user.getUsername());
        Assertions.assertTrue(user.getAuthorities().isEmpty());
    }

    /**
     * Verifies that reading a not existing user throws an exception.
     */
    @Test
    public final void testGetUser_NotExisting_Exception() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("abc"));
    }

}
