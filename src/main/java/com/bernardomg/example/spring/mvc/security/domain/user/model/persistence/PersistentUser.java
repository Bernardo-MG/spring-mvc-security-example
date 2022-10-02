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

package com.bernardomg.example.spring.mvc.security.domain.user.model.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * Persistent implementation of {@code User}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Entity(name = "UserDetails")
@Table(name = "USERS")
@Data
@SequenceGenerator(name = "seq_users_id", initialValue = 10, allocationSize = 1)
public class PersistentUser implements Serializable {

    /**
     * Serialization id.
     */
    @Transient
    private static final long          serialVersionUID   = 4807136960800402795L;

    /**
     * User expired flag.
     */
    @Column(name = "credentials_expired", nullable = false)
    private Boolean                    credentialsExpired = false;

    /**
     * User email.
     */
    @Column(name = "email", nullable = false, length = 60)
    private String                     email;

    /**
     * User enabled flag.
     */
    @Column(name = "enabled", nullable = false)
    private Boolean                    enabled            = true;

    /**
     * User expired flag.
     */
    @Column(name = "expired", nullable = false)
    private Boolean                    expired            = false;

    /**
     * Entity id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users_id")
    @Column(name = "id", nullable = false, unique = true)
    private Long                       id;

    /**
     * User locked flag.
     */
    @Column(name = "locked", nullable = false)
    private Boolean                    locked             = false;

    /**
     * User password.
     */
    @Column(name = "password", nullable = false, length = 60)
    private String                     password;

    /**
     * Granted roles.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<PersistentRole> roles              = new ArrayList<>();

    /**
     * User name.
     */
    @Column(name = "username", nullable = false, unique = true, length = 60)
    private String                     username;

    /**
     * Default constructor.
     */
    public PersistentUser() {
        super();
    }

}
