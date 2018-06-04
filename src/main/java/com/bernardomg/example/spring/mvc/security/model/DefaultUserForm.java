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

package com.bernardomg.example.spring.mvc.security.model;

/**
 * Default implementation of the user form.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class DefaultUserForm implements UserForm {

    /**
     * Enabled flag.
     */
    private Boolean enabled;

    /**
     * Expired flag.
     */
    private Boolean expired;

    /**
     * Locked flag.
     */
    private Boolean locked;

    /**
     * The password.
     */
    private String  password;

    /**
     * The username.
     */
    private String  username;

    /**
     * Default constructor.
     */
    public DefaultUserForm() {
        super();
    }

    @Override
    public final Boolean getEnabled() {
        return enabled;
    }

    @Override
    public final Boolean getExpired() {
        return expired;
    }

    @Override
    public final Boolean getLocked() {
        return locked;
    }

    @Override
    public final String getPassword() {
        return password;
    }

    @Override
    public final String getUsername() {
        return username;
    }

    /**
     * Sets the enabled flag.
     * 
     * @param enabled
     *            the enabled flag
     */
    public final void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Sets the expired flag.
     * 
     * @param expired
     *            the expired flag
     */
    public final void setExpired(final Boolean expired) {
        this.expired = expired;
    }

    /**
     * Sets the locked flag.
     * 
     * @param locked
     *            the locked flag
     */
    public final void setLocked(final Boolean locked) {
        this.locked = locked;
    }

    /**
     * Sets the password used to authenticate the user.
     * 
     * @param password
     *            the new password
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Sets the username used to authenticate the user.
     * 
     * @param username
     *            the new username
     */
    public final void setUsername(final String username) {
        this.username = username;
    }

}
