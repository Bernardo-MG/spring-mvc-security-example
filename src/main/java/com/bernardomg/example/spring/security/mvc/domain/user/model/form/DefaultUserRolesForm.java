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

package com.bernardomg.example.spring.security.mvc.domain.user.model.form;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bernardomg.example.spring.security.mvc.validation.group.Creation;
import com.bernardomg.example.spring.security.mvc.validation.group.Update;

/**
 * Default implementation of the user roles form.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class DefaultUserRolesForm implements UserRolesForm {

    /**
     * Enabled flag.
     */
    @NotNull
    private Collection<String> roles;

    /**
     * The username.
     */
    @NotNull(groups = { Creation.class, Update.class })
    @Size(min = 3, max = 20, groups = { Creation.class, Update.class })
    private String             username = "";

    /**
     * Default constructor.
     */
    public DefaultUserRolesForm() {
        super();
    }

    @Override
    public final Collection<String> getRoles() {
        return roles;
    }

    @Override
    public final String getUsername() {
        return username;
    }

    /**
     * Sets the user roles.
     *
     * @param rls
     *            the user roles
     */
    public final void setRoles(final Collection<String> rls) {
        roles = rls;
    }

    /**
     * Sets the username used to authenticate the user.
     *
     * @param value
     *            the new username
     */
    public final void setUsername(final String value) {
        username = value;
    }

}
