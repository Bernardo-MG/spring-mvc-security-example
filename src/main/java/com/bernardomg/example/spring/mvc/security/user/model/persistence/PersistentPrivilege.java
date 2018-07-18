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

package com.bernardomg.example.spring.mvc.security.user.model.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.bernardomg.example.spring.mvc.security.user.model.Privilege;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Iterables;

/**
 * Persistent implementation of {@code Privilege}.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Entity(name = "Privilege")
@Table(name = "PRIVILEGES")
public class PersistentPrivilege implements Privilege, Serializable {

    /**
     * Serialization id.
     */
    private static final long                serialVersionUID = 8513041662486312372L;

    /**
     * Entity id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long                             id;

    /**
     * Authority name.
     */
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String                           name;

    /**
     * Users with the role.
     */
    @ManyToMany(mappedBy = "roles")
    private final Collection<PersistentUser> users            = new ArrayList<>();

    /**
     * Default constructor.
     */
    public PersistentPrivilege() {
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

        final PersistentPrivilege other = (PersistentPrivilege) obj;
        return Objects.equals(name, other.name);
    }

    /**
     * Returns the user id.
     * 
     * @return the user id
     */
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the users with the role.
     * 
     * @return the users with the role
     */
    public Collection<PersistentUser> getUsers() {
        return Collections.unmodifiableCollection(users);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(name);
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
     * Sets the role name.
     * 
     * @param role
     *            new name
     */
    public void setName(final String role) {
        name = role;
    }

    /**
     * Sets the users with the role.
     * 
     * @param usrs
     *            the users with the role
     */
    public void setUsers(final Collection<PersistentUser> usrs) {
        users.clear();

        Iterables.addAll(users, usrs);
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("name", name).toString();
    }

}
