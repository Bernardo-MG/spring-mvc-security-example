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

package com.bernardomg.example.spring.mvc.security.controller.users;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bernardomg.example.spring.mvc.security.service.UserService;

/**
 * Secured controller.
 * <p>
 * It makes use of role-based security.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Controller
@RequestMapping("/users")
public final class UserController {

    /**
     * Users list param.
     */
    public static final String PARAM_USERS    = "users";

    /**
     * Users list view.
     */
    public static final String USER_LIST_VIEW = "user/list";

    /**
     * Users service.
     */
    private final UserService  service;

    /**
     * Constructs a login controller.
     * 
     * @param userService
     *            users service
     */
    @Autowired
    public UserController(final UserService userService) {
        super();

        service = checkNotNull(userService,
                "Received a null pointer as users service");
    }

    /**
     * Shows a page with all the users.
     * 
     * @param model
     *            data model
     * @return the admin view
     */
    @GetMapping
    public final String showAdminPage(final ModelMap model) {
        model.put(PARAM_USERS, getService().getAllUsers());

        return USER_LIST_VIEW;
    }

    /**
     * Returns the users service.
     * 
     * @return the users service
     */
    private final UserService getService() {
        return service;
    }

}
