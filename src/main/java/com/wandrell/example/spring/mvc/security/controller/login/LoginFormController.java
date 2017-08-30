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

package com.wandrell.example.spring.mvc.security.controller.login;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wandrell.example.spring.mvc.security.controller.dto.LoginForm;
import com.wandrell.example.spring.mvc.security.model.persistence.DefaultExampleEntity;

/**
 * Controller for the example entities form view.
 * <p>
 * This serves as an adapter between the UI and the services layer.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Controller
@RequestMapping("/login")
public class LoginFormController {

    /**
     * Logger for the service.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(LoginFormController.class);

    /**
     * Constructs a controller with the specified dependencies.
     */
    @Autowired
    public LoginFormController() {
        super();

    }

    /**
     * Returns the initial Sponsor form data.
     * 
     * @return the initial Sponsor form data
     */
    @ModelAttribute(LoginConstants.BEAN_FORM)
    public final LoginForm getEntityForm() {
        return new LoginForm();
    }

    /**
     * Persists an entity.
     * 
     * @return the next view to show
     */
    @GetMapping
    public final String showForm() {
        return LoginConstants.VIEW_LOGIN;
    }

}
