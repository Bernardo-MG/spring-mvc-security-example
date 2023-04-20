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

package com.bernardomg.example.spring.security.mvc.test.security.security.integration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bernardomg.example.spring.security.mvc.test.configuration.annotation.MvcIntegrationTest;

@MvcIntegrationTest
@DisplayName("Requests with CSRF")
public final class ITCsrf {

    /**
     * Mock MVC for the requests.
     */
    private MockMvc               mockMvc;

    /**
     * Web application context.
     */
    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * Default constructor.
     */
    public ITCsrf() {
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

    @Test
    @DisplayName("CSRF supported on POST requests")
    public final void testPost_Csrf_Found() throws Exception {
        final RequestBuilder request;

        request = post("/").with(csrf());

        mockMvc.perform(request)
            .andExpect(status().isFound());
    }

    @Test
    @DisplayName("POST requests without CSRF are rejected")
    public final void testPost_NoCsrf_Forbidden() throws Exception {
        final RequestBuilder request;

        request = post("/");

        mockMvc.perform(request)
            .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("CSRF supported on PUT requests")
    public final void testPut_Csrf_Found() throws Exception {
        final RequestBuilder request;

        request = put("/").with(csrf());

        mockMvc.perform(request)
            .andExpect(status().isFound());
    }

    @Test
    @DisplayName("PUT requests without CSRF are rejected")
    public final void testPut_NoCsrf_Forbidden() throws Exception {
        final RequestBuilder request;

        request = put("/");

        mockMvc.perform(request)
            .andExpect(status().isForbidden());
    }

}
