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

package com.bernardomg.example.spring.security.mvc.test.domain.user.controller.integration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bernardomg.example.spring.security.mvc.security.user.model.PersistentUser;
import com.bernardomg.example.spring.security.mvc.security.user.repository.UserRepository;
import com.bernardomg.example.spring.security.mvc.test.configuration.annotation.MvcIntegrationTest;

/**
 * Integration tests for the users controller, verifying that it can create users.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@MvcIntegrationTest
@Sql("/db/populate/full.sql")
@DisplayName("User controller creation operations")
public class ITUserControllerCreate {

    /**
     * Mock MVC for the requests.
     */
    private MockMvc               mockMvc;

    /**
     * User repository.
     */
    @Autowired
    private UserRepository        repository;

    /**
     * Web application context.
     */
    @Autowired
    private WebApplicationContext webApplicationContext;

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
            .apply(springSecurity())
            .build();
    }

    /**
     * Verifies that users can be created through the controller.
     */
    @Test
    @WithMockUser(username = "admin", authorities = { "CREATE_DATA" })
    @DisplayName("An authenticated user can create other users")
    public final void testCreate() throws Exception {
        final RequestBuilder           request; // Test request
        final Iterable<PersistentUser> users;   // Read users

        request = MockMvcRequestBuilders.post("/users/save")
            .param("username", "username")
            .param("password", "password")
            .with(csrf());

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status()
                .isOk());

        users = repository.findAll();

        Assertions.assertEquals(7, IterableUtils.size(users));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "CREATE_DATA" })
    @DisplayName("Empty passwords are rejected")
    public final void testCreate_EmptyPassword() throws Exception {
        final RequestBuilder request; // Test request

        request = MockMvcRequestBuilders.post("/users/save")
            .param("username", "username")
            .param("password", "")
            .with(csrf());

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status()
                .is4xxClientError());
    }

}
