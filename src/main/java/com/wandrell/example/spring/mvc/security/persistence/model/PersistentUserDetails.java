
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

@Entity(name = "UserDetails")
@Table(name = "users")
public class PersistentUserDetails implements UserDetails {

    @Transient
    private static final long serialVersionUID = 4807136960800402795L;

    @Column(name = "enabled", nullable = false)
    private Boolean           enabled;

    @Column(name = "expired", nullable = false)
    private Boolean           expired;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private final Long        id               = null;

    @Column(name = "locked", nullable = false)
    private Boolean           locked;

    @Column(name = "password", nullable = false)
    private String            password;

    @Column(name = "name", nullable = false, unique = true)
    private String            username;

    public PersistentUserDetails() {
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getExpired() {
        return expired;
    }

    public Long getId() {
        return id;
    }

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

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public void setExpired(final Boolean expired) {
        this.expired = expired;
    }

    public void setLocked(final Boolean locked) {
        this.locked = locked;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

}
