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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bernardomg.example.spring.mvc.security.user.service.UserService;

/**
 * Integration tests for the persistent user service, verifying that users can't
 * be read with an invalid authentication.
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
public class ITUserServiceReadInvalidAuth {

    /**
     * User service being tested.
     */
    @Autowired
    @Qualifier("userService")
    private UserService service;

    /**
     * Default constructor.
     */
    public ITUserServiceReadInvalidAuth() {
        super();
    }

    /**
     * Verifies that trying to read all the users without being authenticated
     * causes an exception.
     */
    @Test
    public final void testGetAllUsers_NoAuth_Exception() {
        Assertions.assertThrows(
                AuthenticationCredentialsNotFoundException.class,
                () -> service.getAllUsers());
    }

    /**
     * Verifies that trying to read all the users without privileges causes an
     * exception.
     */
    @Test
    @WithMockUser
    public final void testGetAllUsers_NoPrivileges_Exception() {
        Assertions.assertThrows(AccessDeniedException.class,
                () -> service.getAllUsers());
    }

    /**
     * Verifies that trying to read a user without being authenticated causes an
     * exception.
     */
    @Test
    public final void testGetUser_NoAuth_Exception() {
        Assertions.assertThrows(
                AuthenticationCredentialsNotFoundException.class,
                () -> service.getUser("noroles"));
    }

    /**
     * Verifies that trying to read a user without privileges causes an
     * exception.
     */
    @Test
    @WithMockUser
    public final void testGetUser_NoPrivileges_Exception() {
        Assertions.assertThrows(AccessDeniedException.class,
                () -> service.getUser("noroles"));
    }

}
