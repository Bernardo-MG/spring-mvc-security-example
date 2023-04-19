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

package com.bernardomg.example.spring.security.mvc.security.user.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.persistence.Transient;
import lombok.Data;

/**
 * Dto implementation of {@code User}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Data
@Entity(name = "User")
@Table(name = "users")
@TableGenerator(name = "seq_DATAs_id", table = "sequences", pkColumnName = "sequence", valueColumnName = "count",
        allocationSize = 1)
public class PersistentUser implements Serializable {

    /**
     * Serialization id.
     */
    @Transient
    private static final long serialVersionUID = 4807136960800402795L;

    /**
     * User expired flag.
     */
    @Column(name = "credentials_expired", nullable = false)
    private Boolean           credentialsExpired;

    /**
     * User email.
     */
    @Column(name = "email", nullable = false, length = 60)
    private String            email;

    /**
     * User enabled flag.
     */
    @Column(name = "enabled", nullable = false)
    private Boolean           enabled;

    /**
     * User expired flag.
     */
    @Column(name = "expired", nullable = false)
    private Boolean           expired;

    /**
     * Entity id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "seq_DATAs_id")
    @Column(name = "id", nullable = false, unique = true)
    private Long              id;

    /**
     * User locked flag.
     */
    @Column(name = "locked", nullable = false)
    private Boolean           locked;

    /**
     * User name.
     */
    @Column(name = "name", nullable = false, unique = true, length = 60)
    private String            name;

    /**
     * User password.
     */
    @Column(name = "password", nullable = false, length = 60)
    private String            password;

    /**
     * User name.
     */
    @Column(name = "username", nullable = false, unique = true, length = 60)
    private String            username;

}
