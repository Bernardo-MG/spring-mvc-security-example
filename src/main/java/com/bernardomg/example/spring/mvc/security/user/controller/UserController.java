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

package com.bernardomg.example.spring.mvc.security.user.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bernardomg.example.spring.mvc.security.model.DefaultUserForm;
import com.bernardomg.example.spring.mvc.security.model.UserForm;
import com.bernardomg.example.spring.mvc.security.user.model.User;
import com.bernardomg.example.spring.mvc.security.user.service.UserService;
import com.bernardomg.example.spring.mvc.security.validation.group.Creation;
import com.bernardomg.example.spring.mvc.security.validation.group.Update;

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
     * Users form param.
     */
    public static final String PARAM_USER_FORM    = "form";

    /**
     * Users list param.
     */
    public static final String PARAM_USERS        = "users";

    /**
     * Users creation form view.
     */
    public static final String VIEW_USER_CREATION = "user/create";

    /**
     * Users edition form view.
     */
    public static final String VIEW_USER_EDITION  = "user/edit";

    /**
     * Users list view.
     */
    public static final String VIEW_USER_LIST     = "user/list";

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
     * Returns the initial user form data.
     * 
     * @return the initial user form data
     */
    @ModelAttribute(PARAM_USER_FORM)
    public final UserForm getEntityForm() {
        return new DefaultUserForm();
    }

    /**
     * Persists a user.
     * 
     * @param model
     *            model map
     * @param form
     *            form data
     * @param bindingResult
     *            binding result
     * @param response
     *            HTTP response
     * @return the next view to show
     */
    @PostMapping("/save")
    public final String saveUser(final ModelMap model,
            @ModelAttribute(PARAM_USER_FORM) @Validated(Creation.class) final UserForm form,
            final BindingResult bindingResult,
            final HttpServletResponse response) {
        final String path;

        if (bindingResult.hasErrors()) {
            // Invalid form data

            // Returns to the form view
            path = VIEW_USER_CREATION;

            // Marks the response as a bad request
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {

            getService().create(form);

            path = showUsersList(model);
        }

        return path;
    }

    /**
     * Shows the user creation view. This is done by returning the name of the
     * view.
     * 
     * @return the name for the entity edition view
     */
    @GetMapping(path = "/create")
    public final String showUserCreation() {
        return VIEW_USER_CREATION;
    }

    /**
     * Shows the user editon view. This is done by returning the name of the
     * view.
     * 
     * @param username
     *            username of the user to edit
     * @param model
     *            data model
     * @return the name for the entity edition view
     */
    @GetMapping(path = "/edit/{username}")
    public final String showUserEdition(
            @PathVariable("username") final String username,
            final ModelMap model) {
        final User user;
        final UserForm form;

        user = getService().getUser(username);
        form = new DefaultUserForm();
        BeanUtils.copyProperties(user, form);
        model.put(PARAM_USER_FORM, form);

        return VIEW_USER_EDITION;
    }

    /**
     * Shows a page with all the users. This is done by returning the name of
     * the view.
     * 
     * @param model
     *            data model
     * @return the admin view
     */
    @GetMapping
    public final String showUsersList(final ModelMap model) {
        model.put(PARAM_USERS, getService().getAllUsers());

        return VIEW_USER_LIST;
    }

    /**
     * Updates a user.
     * 
     * @param model
     *            model map
     * @param form
     *            form data
     * @param bindingResult
     *            binding result
     * @param response
     *            HTTP response
     * @return the next view to show
     */
    @PostMapping("/update")
    public final String updateUser(final ModelMap model,
            @ModelAttribute(PARAM_USER_FORM) @Validated(Update.class) final UserForm form,
            final BindingResult bindingResult,
            final HttpServletResponse response) {
        final String path;

        if (bindingResult.hasErrors()) {
            // Invalid form data

            // Returns to the form view
            path = VIEW_USER_EDITION;

            // Marks the response as a bad request
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            getService().update(form);

            path = showUsersList(model);
        }

        return path;
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
