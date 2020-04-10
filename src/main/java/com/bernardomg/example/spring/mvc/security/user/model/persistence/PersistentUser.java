/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017-2020 the original author or authors.
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

package com.bernardomg.example.spring.mvc.security.user.model.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bernardomg.example.spring.mvc.security.user.model.Role;
import com.bernardomg.example.spring.mvc.security.user.model.User;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;

/**
 * Persistent implementation of {@code User}.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Entity(name = "UserDetails")
@Table(name = "USERS")
public class PersistentUser implements User {

    /**
     * Serialization id.
     */
    @Transient
    private static final long                serialVersionUID   = 4807136960800402795L;

    /**
     * User expired flag.
     */
    @Column(name = "CREDENTIALS_EXPIRED", nullable = false)
    private Boolean                          credentialsExpired = false;

    /**
     * User email.
     */
    @Column(name = "email", nullable = false, length = 60)
    private String                           email;

    /**
     * User enabled flag.
     */
    @Column(name = "enabled", nullable = false)
    private Boolean                          enabled            = true;

    /**
     * User expired flag.
     */
    @Column(name = "expired", nullable = false)
    private Boolean                          expired            = false;

    /**
     * Entity id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long                             id;

    /**
     * User locked flag.
     */
    @Column(name = "locked", nullable = false)
    private Boolean                          locked             = false;

    /**
     * User password.
     */
    @Column(name = "password", nullable = false, length = 60)
    private String                           password;

    /**
     * Granted roles.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))
    private final Collection<PersistentRole> roles              = new ArrayList<>();

    /**
     * User name.
     */
    @Column(name = "name", nullable = false, unique = true, length = 60)
    private String                           username;

    /**
     * Default constructor.
     */
    public PersistentUser() {
        super();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final PersistentUser other = (PersistentUser) obj;
        return Objects.equals(username, other.username);
    }

    @Override
    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user enabled flag.
     * 
     * @return the user enabled flag
     */
    @Override
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Returns the user expired flag.
     * 
     * @return the user expired flag
     */
    @Override
    public Boolean getExpired() {
        return expired;
    }

    /**
     * Returns the user id.
     * 
     * @return the user id
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the user locked flag.
     * 
     * @return the user locked flag
     */
    @Override
    public Boolean getLocked() {
        return locked;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends Role> getRoles() {
        return Collections.unmodifiableCollection(roles);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(username);
    }

    /**
     * Sets the credentials expired flag.
     * 
     * @param flag
     *            the credentials expired flag
     */
    public void setCredentialsExpired(final Boolean flag) {
        credentialsExpired = flag;
    }

    /**
     * Sets the user email.
     * 
     * @param mail
     *            email to set
     */
    public void setEmail(final String mail) {
        email = mail;
    }

    /**
     * Sets the user enabled flag.
     * 
     * @param flag
     *            the user enabled flag
     */
    public void setEnabled(final Boolean flag) {
        enabled = flag;
    }

    /**
     * Sets the user expired flag.
     * 
     * @param flag
     *            the user expired flag
     */
    public void setExpired(final Boolean flag) {
        expired = flag;
    }

    /**
     * Sets the user id.
     * 
     * @param identifier
     *            the new id
     */
    public void setId(final Long identifier) {
        id = identifier;
    }

    /**
     * Sets the user locked flag.
     * 
     * @param flag
     *            the user locked flag
     */
    public void setLocked(final Boolean flag) {
        locked = flag;
    }

    /**
     * Sets the user password.
     * 
     * @param pass
     *            the user password
     */
    public void setPassword(final String pass) {
        password = pass;
    }

    /**
     * Sets the user roles.
     * 
     * @param rls
     *            new roles
     */
    public void setRoles(final Iterable<PersistentRole> rls) {
        roles.clear();

        Iterables.addAll(roles, rls);
    }

    /**
     * Sets the user name.
     * 
     * @param name
     *            the user name
     */
    public void setUsername(final String name) {
        username = name;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("username", username)
                .toString();
    }

}
