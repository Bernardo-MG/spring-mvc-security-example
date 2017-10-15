/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2017 the original author or authors.
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

package com.wandrell.example.spring.mvc.security.persistence.model;

import java.util.Collection;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.google.common.base.MoreObjects;

/**
 * Persistent implementation of {@code GrantedAuthority}.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Entity(name = "GrantedAuthority")
@Table(name = "authorities")
public class PersistentGrantedAuthority implements GrantedAuthority {

    /**
     * Serialization id.
     */
    private static final long                 serialVersionUID = 8513041662486312372L;

    /**
     * Authority name.
     */
    @Column(name = "authority", nullable = false, unique = true)
    private String                            authority;

    /**
     * Entity id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long                              id;

    /**
     * Users with the authority.
     */
    @ManyToMany(mappedBy = "authorities")
    private Collection<PersistentUserDetails> users;

    /**
     * Default constructor.
     */
    public PersistentGrantedAuthority() {
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

        final PersistentGrantedAuthority other = (PersistentGrantedAuthority) obj;
        return Objects.equals(authority, other.authority);
    }

    @Override
    public String getAuthority() {
        return authority;
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
     * Returns the users with the authority.
     * 
     * @return the users with the authority
     */
    public Collection<PersistentUserDetails> getUsers() {
        return users;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(authority);
    }

    /**
     * Sets the authority name.
     * 
     * @param auth
     *            new authority
     */
    public void setAuthority(final String auth) {
        authority = auth;
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
     * Sets the users with the authority.
     * 
     * @param users
     *            the users with the authority
     */
    public void setUsers(final Collection<PersistentUserDetails> users) {
        this.users = users;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("authority", authority)
                .toString();
    }

}
