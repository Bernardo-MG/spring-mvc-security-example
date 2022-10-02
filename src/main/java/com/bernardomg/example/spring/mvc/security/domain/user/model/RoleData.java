/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017-2022 the original author or authors.
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

package com.bernardomg.example.spring.mvc.security.domain.user.model;

import java.util.Collection;

/**
 * User role. Groups a set of permissions.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface RoleData {

    /**
     * Returns the user id.
     *
     * @return the user id
     */
    public Long getId();

    /**
     * Returns the role name.
     *
     * @return the role name
     */
    public String getName();

    /**
     * Returns all the roles privileges.
     *
     * @return roles privileges
     */
    public Collection<PrivilegeData> getPrivileges();

    /**
     * Sets the user id.
     *
     * @param identifier
     *            the new id
     */
    public void setId(final Long identifier);

    /**
     * Sets the role name.
     *
     * @param role
     *            new name
     */
    public void setName(final String role);

}
