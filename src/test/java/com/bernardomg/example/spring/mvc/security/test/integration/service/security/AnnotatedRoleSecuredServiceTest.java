/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017 the original author or authors.
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

package com.bernardomg.example.spring.mvc.security.test.integration.service.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bernardomg.example.spring.mvc.security.service.RoleSecuredService;

/**
 * Integration tests for the annotation-based {@code RoleSecuredService}.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@WebAppConfiguration
@ContextConfiguration(
        locations = { "classpath:context/application-context.xml" })
public final class AnnotatedRoleSecuredServiceTest {

    /**
     * Secured service being tested.
     */
    @Autowired
    @Qualifier("annotatedRoleSecuredService")
    private RoleSecuredService service;

    /**
     * Default constructor.
     */
    public AnnotatedRoleSecuredServiceTest() {
        super();
    }

    /**
     * Verifies that trying to invoke the method without authorization data
     * causes an exception.
     */
    @Test
    public final void testAdminMethod_NotAuthData_Exception() {
        Assertions.assertThrows(
                AuthenticationCredentialsNotFoundException.class,
                service::adminMethod,
                "An Authentication object was not found in the SecurityContext");
    }

}
