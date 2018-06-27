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

package com.bernardomg.example.spring.mvc.security.test.integration.controller.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Integration tests for the login procedure.
 * <p>
 * Verifies that the login URL handles login attempts.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@WebAppConfiguration
@ContextConfiguration(
        locations = { "classpath:context/application-test-context.xml" })
public final class ITLogin {

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
    public ITLogin() {
        super();
    }

    /**
     * Sets up the mock MVC.
     */
    @BeforeEach
    public final void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    /**
     * Verifies that using a disabled user redirects to the login error URL.
     */
    @Test
    public final void testLogin_DisabledUser_ErrorRedirect() throws Exception {
        final FormLoginRequestBuilder login; // Login request

        login = formLogin().user("disabled").password("1111");

        mockMvc.perform(login).andExpect(redirectedUrl("/login?error=true"));
    }

    /**
     * Verifies that using a disabled user name fails the login.
     */
    @Test
    public final void testLogin_DisabledUser_Unauthenticated()
            throws Exception {
        final FormLoginRequestBuilder login; // Login request

        login = formLogin().user("disabled").password("1111");

        mockMvc.perform(login).andExpect(unauthenticated());
    }

    /**
     * Verifies that using an expired user redirects to the login error URL.
     */
    @Test
    public final void testLogin_ExpiredUser_ErrorRedirect() throws Exception {
        final FormLoginRequestBuilder login; // Login request

        login = formLogin().user("expired").password("1111");

        mockMvc.perform(login).andExpect(redirectedUrl("/login?error=true"));
    }

    /**
     * Verifies that using an expired user name fails the login.
     */
    @Test
    public final void testLogin_ExpiredUser_Unauthenticated() throws Exception {
        final FormLoginRequestBuilder login; // Login request

        login = formLogin().user("expired").password("1111");

        mockMvc.perform(login).andExpect(unauthenticated());
    }

    /**
     * Verifies that using an invalid password redirects to the login error URL.
     */
    @Test
    public final void testLogin_InvalidPassword_ErrorRedirect()
            throws Exception {
        final FormLoginRequestBuilder login; // Login request

        login = formLogin().user("admin").password("abc");

        mockMvc.perform(login).andExpect(redirectedUrl("/login?error=true"));
    }

    /**
     * Verifies that using an invalid user name fails the login.
     */
    @Test
    public final void testLogin_InvalidPassword_Unauthenticated()
            throws Exception {
        final FormLoginRequestBuilder login; // Login request

        login = formLogin().user("admin").password("abc");

        mockMvc.perform(login).andExpect(unauthenticated());
    }

    /**
     * Verifies that using an invalid user redirects to the login error URL.
     */
    @Test
    public final void testLogin_InvalidUser_ErrorRedirect() throws Exception {
        final FormLoginRequestBuilder login; // Login request

        login = formLogin().user("abc").password("1234");

        mockMvc.perform(login).andExpect(redirectedUrl("/login?error=true"));
    }

    /**
     * Verifies that using an invalid user name fails the login.
     */
    @Test
    public final void testLogin_InvalidUser_Unauthenticated() throws Exception {
        final FormLoginRequestBuilder login; // Login request

        login = formLogin().user("abc").password("1234");

        mockMvc.perform(login).andExpect(unauthenticated());
    }

    /**
     * Verifies that using a locked user redirects to the login error URL.
     */
    @Test
    public final void testLogin_LockedUser_ErrorRedirect() throws Exception {
        final FormLoginRequestBuilder login; // Login request

        login = formLogin().user("locked").password("1111");

        mockMvc.perform(login).andExpect(redirectedUrl("/login?error=true"));
    }

    /**
     * Verifies that using a locked user name fails the login.
     */
    @Test
    public final void testLogin_LockedUser_Unauthenticated() throws Exception {
        final FormLoginRequestBuilder login; // Login request

        login = formLogin().user("locked").password("1111");

        mockMvc.perform(login).andExpect(unauthenticated());
    }

    /**
     * Verifies that using a valid user name allows to login.
     */
    @Test
    public final void testLogin_ValidUser_Authenticated() throws Exception {
        final FormLoginRequestBuilder login; // Login request

        login = formLogin().user("admin").password("1234");

        mockMvc.perform(login).andExpect(authenticated().withUsername("admin"));
    }

}
