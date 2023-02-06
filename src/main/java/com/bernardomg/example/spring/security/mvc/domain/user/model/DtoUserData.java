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

import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;

/**
 * Persistent implementation of {@code User}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
public class DtoUserData implements UserData {

    /**
     * User expired flag.
     */
    private Boolean              credentialsExpired = false;

    /**
     * User email.
     */
    private String               email;

    /**
     * User enabled flag.
     */
    private Boolean              enabled            = true;

    /**
     * User expired flag.
     */
    private Boolean              expired            = false;

    /**
     * Entity id.
     */
    private Long                 id;

    /**
     * User locked flag.
     */
    private Boolean              locked             = false;

    /**
     * User password.
     */
    private String               password;

    /**
     * Granted roles.
     */
    private Collection<RoleData> roles              = new ArrayList<>();

    /**
     * User name.
     */
    private String               username;

}
