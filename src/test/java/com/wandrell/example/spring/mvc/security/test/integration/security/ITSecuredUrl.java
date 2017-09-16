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

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
 * Integration tests for the secured URLs.
 * <p>
 * Verifies that URLs are secured against anonymous access.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@WebAppConfiguration
@ContextConfiguration(
        locations = { "classpath:context/application-context.xml" })
public class ITSecuredUrl extends AbstractTestNGSpringContextTests {

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
    public ITSecuredUrl() {
        super();
    }

    /**
     * Sets up the mock MVC.
     */
    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    /**
     * Verifies that the home URL allows access to authenticated users.
     */
    @Test
    public void testHome__requiresAuthentication() throws Exception {
        // TODO: Make this test work
        // mockMvc.perform(get("/").with(httpBasic("admin",
        // "1234")).with(csrf()))
        // .andExpect(status().isOk())
        // .andExpect(authenticated().withUsername("admin"));
    }

    /**
     * Verifies that the home URL is secured against anonymous access.
     */
    @Test
    public void testHome_requiresAuthentication() throws Exception {
        // Home redirects to the login view
        mockMvc.perform(get("/")).andExpect(status().isFound())
                .andExpect(unauthenticated());
    }

    /**
     * Verifies that the static resources URL allows anonymous access.
     */
    @Test
    public void testStatic_requiresAuthentication() throws Exception {
        // Allowed to access, but no resource found
        mockMvc.perform(get("/static/")).andExpect(status().isNotFound())
                .andExpect(unauthenticated());
    }

}
