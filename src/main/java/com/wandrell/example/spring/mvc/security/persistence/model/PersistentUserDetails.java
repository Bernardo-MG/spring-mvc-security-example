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
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User detail prepared to be persisted.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Entity(name = "UserDetails")
@Table(name = "users")
public class PersistentUserDetails implements UserDetails {

    /**
     * Serialization id.
     */
    @Transient
    private static final long serialVersionUID = 4807136960800402795L;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private final Long        id               = null;

    /**
     * User locked flag.
     */
    @Column(name = "locked", nullable = false)
    private Boolean           locked;

    /**
     * User password.
     */
    @Column(name = "password", nullable = false)
    private String            password;

    /**
     * User name.
     */
    @Column(name = "name", nullable = false, unique = true)
    private String            username;

    /**
     * Default constructor.
     */
    public PersistentUserDetails() {
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /**
     * Returns the user enabled flag.
     * 
     * @return the user enabled flag
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Returns the user expired flag.
     * 
     * @return the user expired flag
     */
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
    public Boolean getLocked() {
        return locked;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !expired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the user enabled flag.
     * 
     * @param enabled
     *            the user enabled flag
     */
    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Sets the user expired flag.
     * 
     * @param enabled
     *            the user expired flag
     */
    public void setExpired(final Boolean expired) {
        this.expired = expired;
    }

    /**
     * Sets the user locked flag.
     * 
     * @param enabled
     *            the user locked flag
     */
    public void setLocked(final Boolean locked) {
        this.locked = locked;
    }

    /**
     * Sets the user password.
     * 
     * @param password
     *            the user password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Sets the user name.
     * 
     * @param password
     *            the user name
     */
    public void setUsername(final String username) {
        this.username = username;
    }

}
