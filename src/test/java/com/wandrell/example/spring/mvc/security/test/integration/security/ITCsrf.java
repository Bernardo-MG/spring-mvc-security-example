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

package com.wandrell.example.spring.mvc.security.test.integration.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Integration tests for requests with CSRF.
 * <p>
 * Verifies that the CSRF token is required for modification requests.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@WebAppConfiguration
@ContextConfiguration(
        locations = { "classpath:context/application-context.xml" })
public class ITCsrf extends AbstractTestNGSpringContextTests {

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
     * Verifies that POST requests with CSRF are supported.
     */
    @Test
    public void post_Csrf_Accepted() throws Exception {
        mockMvc.perform(post("/").with(csrf())).andExpect(status().isFound());
    }

    /**
     * Verifies that POST requests without CSRF are rejected.
     */
    @Test
    public void post_NoCsrf_Rejected() throws Exception {
        mockMvc.perform(post("/")).andExpect(status().isForbidden());
    }

    /**
     * Verifies that PUT requests with CSRF are supported.
     */
    @Test
    public void put_Csrf_Accepted() throws Exception {
        mockMvc.perform(put("/").with(csrf())).andExpect(status().isFound());
    }

    /**
     * Verifies that PUT requests without CSRF are rejected.
     */
    @Test
    public void put_NoCsrf_Rejected() throws Exception {
        mockMvc.perform(put("/")).andExpect(status().isForbidden());
    }

    /**
     * Sets up the mock MVC.
     */
    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

}
