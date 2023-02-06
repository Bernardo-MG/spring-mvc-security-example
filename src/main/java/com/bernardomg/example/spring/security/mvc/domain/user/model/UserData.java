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

package com.bernardomg.example.spring.security.mvc.domain.user.model;

import java.util.Collection;

/**
 * User, and all its authentication data.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface UserData {

    /**
     * Returns the credentials expired flag.
     * <p>
     * This usually means that the password is no longer valid.
     *
     * @return the credentials expired flag
     */
    public Boolean getCredentialsExpired();

    /**
     * Returns the user email.
     *
     * @return the user email
     */
    public String getEmail();

    /**
     * Returns the user enabled flag.
     *
     * @return the user enabled flag
     */
    public Boolean getEnabled();

    /**
     * Returns the user expired flag.
     * <p>
     * This means the user is no longer valid.
     *
     * @return the user expired flag
     */
    public Boolean getExpired();

    /**
     * Returns the user id.
     *
     * @return the user id
     */
    public Long getId();

    /**
     * Returns the user locked flag.
     *
     * @return the user locked flag
     */
    public Boolean getLocked();

    /**
     * Returns the user password.
     *
     * @return the user password
     */
    public String getPassword();

    /**
     * Returns all the roles for the user.
     *
     * @return user roles
     */
    public Collection<RoleData> getRoles();

    /**
     * Returns the user username.
     *
     * @return the user username
     */
    public String getUsername();

}
