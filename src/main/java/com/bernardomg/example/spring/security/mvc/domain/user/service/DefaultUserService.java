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

package com.bernardomg.example.spring.security.mvc.domain.user.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bernardomg.example.spring.security.mvc.domain.user.model.DtoPrivilegeData;
import com.bernardomg.example.spring.security.mvc.domain.user.model.DtoRoleData;
import com.bernardomg.example.spring.security.mvc.domain.user.model.DtoUserData;
import com.bernardomg.example.spring.security.mvc.domain.user.model.PrivilegeData;
import com.bernardomg.example.spring.security.mvc.domain.user.model.RoleData;
import com.bernardomg.example.spring.security.mvc.domain.user.model.UserData;
import com.bernardomg.example.spring.security.mvc.domain.user.model.form.UserForm;
import com.bernardomg.example.spring.security.mvc.domain.user.model.form.UserRolesForm;
import com.bernardomg.example.spring.security.mvc.security.user.model.PersistentPrivilege;
import com.bernardomg.example.spring.security.mvc.security.user.model.PersistentRole;
import com.bernardomg.example.spring.security.mvc.security.user.model.PersistentUser;
import com.bernardomg.example.spring.security.mvc.security.user.repository.PrivilegeRepository;
import com.bernardomg.example.spring.security.mvc.security.user.repository.RoleRepository;
import com.bernardomg.example.spring.security.mvc.security.user.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Users service based on {@link PersistentUser} and Spring classes.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
@Slf4j
public final class DefaultUserService implements UserService {

    /**
     * Password encoder.
     */
    private final PasswordEncoder     passwordEncoder;

    /**
     * Privileges repository.
     */
    private final PrivilegeRepository privilegeRepository;

    /**
     * Users repository.
     */
    private final RoleRepository      roleRepository;

    /**
     * Users repository.
     */
    private final UserRepository      userRepository;

    @Override
    public final void create(final UserForm user) {
        final PersistentUser entity;
        final String         encodedPassword;

        Objects.requireNonNull(user);

        entity = new PersistentUser();

        entity.setUsername(user.getUsername());

        // TODO: These values should come from the frontend
        entity.setName(user.getUsername());
        entity.setEmail(user.getUsername());

        // TODO: This flag should come from the frontend
        entity.setCredentialsExpired(false);
        entity.setEnabled(user.getEnabled());
        entity.setExpired(user.getExpired());
        entity.setLocked(user.getLocked());

        if (user.getPassword() == null) {
            // Let the persistence layer handle this error
            encodedPassword = null;
        } else {
            // Password is encoded
            encodedPassword = passwordEncoder.encode(user.getPassword());
        }
        entity.setPassword(encodedPassword);

        userRepository.save(entity);
    }

    @Override
    public final List<RoleData> getAllRoles() {
        return roleRepository.findAll()
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public final List<UserData> getAllUsers() {
        return userRepository.findAll()
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public final Collection<RoleData> getRoles(final String username) {
        final Optional<PersistentUser> read;
        final Collection<RoleData>     roles;

        Objects.requireNonNull(username);

        read = userRepository.findOneByUsername(username);

        if (read.isPresent()) {
            roles = roleRepository.findForUser(read.get()
                .getId())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        } else {
            // TODO: Throw an exception maybe?
            log.warn("User {} not found", username);
            roles = Collections.emptyList();
        }

        return roles;
    }

    @Override
    public final UserData getUser(final String username) {
        final Optional<PersistentUser> read;
        final UserData                 user;

        Objects.requireNonNull(username);

        read = userRepository.findOneByUsername(username);

        if (read.isPresent()) {
            user = toDto(read.get());
        } else {
            // TODO: Throw an exception maybe?
            log.warn("User {} not found", username);
            user = null;
        }

        return user;
    }

    @Override
    public final void update(final UserForm user) {
        final PersistentUser entity;
        final String         encodedPassword;

        Objects.requireNonNull(user);

        entity = userRepository.findOneByUsername(user.getUsername())
            .get();

        BeanUtils.copyProperties(user, entity);

        if (user.getPassword() == null) {
            // Let the persistence layer handle this error
            encodedPassword = null;
        } else {
            // Password is encoded
            encodedPassword = passwordEncoder.encode(user.getPassword());
        }
        entity.setPassword(encodedPassword);

        userRepository.save(entity);
    }

    @Override
    public final void updateRoles(final UserRolesForm userRoles) {
        final Iterable<PersistentRole> roles;
        final Optional<PersistentUser> read;
        final PersistentUser           user;

        Objects.requireNonNull(userRoles);

        read = userRepository.findOneByUsername(userRoles.getUsername());

        if (read.isPresent()) {
            user = read.get();

            userRepository.save(user);

            roles = roleRepository.findByNameIn(userRoles.getRoles());
            for (final PersistentRole role : roles) {
                roleRepository.registerForUser(user.getId(), role.getId());
            }
        } else {
            log.warn("User {} not found", userRoles.getUsername());
        }
    }

    private final PrivilegeData toDto(final PersistentPrivilege entity) {
        final DtoPrivilegeData privilege;

        privilege = new DtoPrivilegeData();
        BeanUtils.copyProperties(entity, privilege);

        return privilege;
    }

    private final RoleData toDto(final PersistentRole entity) {
        final DtoRoleData               role;
        final Collection<PrivilegeData> privilegesData;

        privilegesData = privilegeRepository.findForUser(entity.getId())
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());

        role = new DtoRoleData();
        BeanUtils.copyProperties(entity, role);
        role.setPrivileges(privilegesData);

        return role;
    }

    private final UserData toDto(final PersistentUser entity) {
        final DtoUserData          user;
        final Collection<RoleData> rolesData;

        rolesData = roleRepository.findForUser(entity.getId())
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());

        user = new DtoUserData();
        BeanUtils.copyProperties(entity, user);
        user.setRoles(rolesData);

        return user;
    }

}
