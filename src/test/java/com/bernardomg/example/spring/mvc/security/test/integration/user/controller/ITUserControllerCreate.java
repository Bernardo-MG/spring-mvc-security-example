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

package com.bernardomg.example.spring.mvc.security.test.integration.user.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.bernardomg.example.spring.mvc.security.user.model.User;
import com.bernardomg.example.spring.mvc.security.user.repository.PersistentUserDetailsRepository;
import com.google.common.collect.Iterables;

/**
 * Integration tests for the users controller, verifying that it can create
 * users.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@WebAppConfiguration
@ContextConfiguration(
        locations = { "classpath:context/application-test-context.xml" })
@Transactional
@Rollback
public class ITUserControllerCreate {

    /**
     * Mock MVC for the requests.
     */
    private MockMvc                         mockMvc;

    /**
     * User repository.
     */
    @Autowired
    private PersistentUserDetailsRepository repository;

    /**
     * Web application context.
     */
    @Autowired
    private WebApplicationContext           webApplicationContext;

    /**
     * Default constructor.
     */
    public ITUserControllerCreate() {
        super();
    }

    /**
     * Sets up the mock MVC.
     */
    @BeforeEach
    public final void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).alwaysExpect(status().isOk()).build();
    }

    /**
     * Verifies that users can be created through the controller.
     */
    @Test
    @WithMockUser(username = "admin", authorities = { "CREATE_USER" })
    public final void testCreate() throws Exception {
        final RequestBuilder request; // Test request
        final Iterable<? extends User> users; // Read users

        request = MockMvcRequestBuilders.post("/users/save")
                .param("username", "username").param("password", "password")
                .with(csrf());

        mockMvc.perform(request);

        users = repository.findAll();

        Assertions.assertEquals(7, Iterables.size(users));
    }

}
