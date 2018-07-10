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

package com.bernardomg.example.spring.mvc.security.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for the login view.
 * <p>
 * It takes care of setting up the view for the login form. But it doesn't
 * handle the login procedure, that is taken care by Spring security.
 * <p>
 * The template engine will take care of building the login page, but the
 * controller will receive, and send to the view, the error flag.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Controller
@RequestMapping("/login")
public final class LoginFormController {

    /**
     * Login view.
     */
    private static final String LOGIN_VIEW = "login";

    /**
     * Constructs a login controller.
     */
    @Autowired
    public LoginFormController() {
        super();
    }

    /**
     * Shows the login form.
     * <p>
     * It also handles the boolean parameter which indicates if there was an
     * error during login. This parameter is sent to the view, which will show a
     * warning.
     * 
     * @param model
     *            data model
     * @param error
     *            indicates an error during login
     * @return the login form view
     */
    @GetMapping
    public final String showForm(final Model model,
            @RequestParam(name = "error", required = false,
                    defaultValue = "false") final Boolean error) {

        // Adds the error status
        model.addAttribute("error", error);

        return LOGIN_VIEW;
    }

}
