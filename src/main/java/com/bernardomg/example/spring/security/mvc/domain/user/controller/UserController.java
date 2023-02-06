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

package com.bernardomg.example.spring.security.mvc.domain.user.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bernardomg.example.spring.security.mvc.domain.user.model.RoleData;
import com.bernardomg.example.spring.security.mvc.domain.user.model.UserData;
import com.bernardomg.example.spring.security.mvc.domain.user.model.form.DefaultUserForm;
import com.bernardomg.example.spring.security.mvc.domain.user.model.form.DefaultUserRolesForm;
import com.bernardomg.example.spring.security.mvc.domain.user.model.form.UserForm;
import com.bernardomg.example.spring.security.mvc.domain.user.model.form.UserRolesForm;
import com.bernardomg.example.spring.security.mvc.domain.user.service.UserService;
import com.bernardomg.example.spring.security.mvc.validation.group.Creation;
import com.bernardomg.example.spring.security.mvc.validation.group.Update;

import lombok.AllArgsConstructor;

/**
 * Secured controller.
 * <p>
 * It makes use of role-based security.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    /**
     * User roles form param.
     */
    public static final String PARAM_ROLES            = "roles";

    /**
     * Users form param.
     */
    public static final String PARAM_ROLES_FORM       = "formRoles";

    /**
     * Users form param.
     */
    public static final String PARAM_USER_FORM        = "form";

    /**
     * Users list param.
     */
    public static final String PARAM_USERS            = "users";

    /**
     * Users creation form view.
     */
    public static final String VIEW_USER_CREATION     = "user/create";

    /**
     * Users edition form view.
     */
    public static final String VIEW_USER_EDITION      = "user/edit";

    /**
     * Users list view.
     */
    public static final String VIEW_USER_LIST         = "user/list";

    /**
     * Users edition form view.
     */
    public static final String VIEW_USER_ROLE_EDITION = "user/role/edit";

    /**
     * Users service.
     */
    private final UserService  service;

    /**
     * Returns the initial user form data.
     *
     * @return the initial user form data
     */
    @ModelAttribute(PARAM_USER_FORM)
    public UserForm getUserForm() {
        return new DefaultUserForm();
    }

    /**
     * Returns the initial user roles form data.
     *
     * @return the initial user roles form data
     */
    @ModelAttribute(PARAM_ROLES_FORM)
    public UserRolesForm getUserRolesForm() {
        return new DefaultUserRolesForm();
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
    public String saveUser(final ModelMap model,
            @ModelAttribute(PARAM_USER_FORM) @Validated(Creation.class) final DefaultUserForm form,
            final BindingResult bindingResult, final HttpServletResponse response) {
        final String path;

        if (bindingResult.hasErrors()) {
            // Invalid form data

            // Returns to the form view
            path = VIEW_USER_CREATION;

            // Marks the response as a bad request
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            service.create(form);

            path = showUsersList(model);
        }

        return path;
    }

    /**
     * Shows the user creation view. This is done by returning the name of the view.
     *
     * @return the name for the entity edition view
     */
    @GetMapping(path = "/create")
    public String showUserCreation() {
        return VIEW_USER_CREATION;
    }

    /**
     * Shows the user edition view. This is done by returning the name of the view.
     *
     * @param username
     *            username of the user to edit
     * @param form
     *            form bean
     * @param model
     *            data model
     * @return the name for the user edition view
     */
    @GetMapping(path = "/edit/{username}")
    public String showUserEdition(@PathVariable("username") final String username,
            @ModelAttribute(PARAM_USER_FORM) final DefaultUserForm form, final ModelMap model) {
        final UserData user;

        user = service.getUser(username);
        BeanUtils.copyProperties(user, form);

        model.put(PARAM_USER_FORM, form);

        return VIEW_USER_EDITION;
    }

    /**
     * Shows the user role edition view. This is done by returning the name of the view.
     *
     * @param username
     *            username of the user to edit
     * @param form
     *            form bean
     * @param model
     *            data model
     * @return the name for the user role edition view
     */
    @GetMapping(path = "/roles/edit/{username}")
    public String showUserRoleEdition(@PathVariable("username") final String username,
            @ModelAttribute(PARAM_ROLES_FORM) final DefaultUserRolesForm form, final ModelMap model) {
        final UserData             user;
        final Collection<RoleData> roles;
        final Iterable<RoleData>   allRoles;
        final Collection<String>   roleNames;

        roles = service.getRoles(username);
        roleNames = roles.stream()
            .map(RoleData::getName)
            .collect(Collectors.toList());
        form.setRoles(roleNames);

        user = service.getUser(username);
        BeanUtils.copyProperties(user, form);
        form.setRoles(roleNames);

        allRoles = service.getAllRoles();

        model.put(PARAM_USER_FORM, form);
        model.put(PARAM_ROLES, allRoles);

        return VIEW_USER_ROLE_EDITION;
    }

    /**
     * Shows a page with all the users. This is done by returning the name of the view.
     *
     * @param model
     *            data model
     * @return the admin view
     */
    @GetMapping
    public String showUsersList(final ModelMap model) {
        model.put(PARAM_USERS, service.getAllUsers());

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
    public String updateUser(final ModelMap model,
            @ModelAttribute(PARAM_USER_FORM) @Validated(Update.class) final DefaultUserForm form,
            final BindingResult bindingResult, final HttpServletResponse response) {
        final String path;

        if (bindingResult.hasErrors()) {
            // Invalid form data

            // Returns to the form view
            path = VIEW_USER_EDITION;

            // Marks the response as a bad request
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            service.update(form);

            path = showUsersList(model);
        }

        return path;
    }

    /**
     * Updates the roles list for a user.
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
    @PostMapping("/roles/update")
    public String updateUserRoles(final ModelMap model,
            @ModelAttribute(PARAM_ROLES_FORM) @Validated final DefaultUserRolesForm form,
            final BindingResult bindingResult, final HttpServletResponse response) {
        final String path;

        if (bindingResult.hasErrors()) {
            // Invalid form data

            // Returns to the form view
            path = VIEW_USER_ROLE_EDITION;

            // Marks the response as a bad request
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            service.updateRoles(form);

            path = showUsersList(model);
        }

        return path;
    }

}
