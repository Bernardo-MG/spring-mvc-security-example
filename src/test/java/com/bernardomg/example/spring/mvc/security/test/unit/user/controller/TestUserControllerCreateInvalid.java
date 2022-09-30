/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017-2020 the original author or authors.
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

package com.bernardomg.example.spring.mvc.security.test.unit.user.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bernardomg.example.spring.mvc.security.user.controller.UserController;
import com.bernardomg.example.spring.mvc.security.user.service.UserService;

/**
 * Integration tests for the secured URLs.
 * <p>
 * Verifies that URLs are secured against anonymous access.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@DisplayName("User controller creation operations invalid parameters")
public class TestUserControllerCreateInvalid {

    /**
     * Mock MVC for the requests.
     */
    private MockMvc mockMvc;

    /**
     * Default constructor.
     */
    public TestUserControllerCreateInvalid() {
        super();
    }

    /**
     * Sets up the mock MVC.
     */
    @BeforeEach
    public final void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(getController())
            .alwaysExpect(status().is4xxClientError())
            .build();
    }

    @Test
    @DisplayName("Invalid parameters are accepted")
    public final void testCreate_EmptyPassword() throws Exception {
        final RequestBuilder request; // Test request

        request = MockMvcRequestBuilders.post("/users/save")
            .param("username", "username")
            .param("password", "");

        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.model()
                .attributeHasFieldErrors(UserController.PARAM_USER_FORM, "password"));
    }

    /**
     * Returns a controller with mocked dependencies.
     *
     * @return a mocked controller
     */
    private final UserController getController() {
        final UserService service; // Mocked service

        service = Mockito.mock(UserService.class);

        return new UserController(service);
    }

}
