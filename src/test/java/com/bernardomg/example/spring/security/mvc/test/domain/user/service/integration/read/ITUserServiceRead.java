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

package com.bernardomg.example.spring.security.mvc.test.domain.user.service.integration.read;

import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.example.spring.security.mvc.domain.user.model.PrivilegeData;
import com.bernardomg.example.spring.security.mvc.domain.user.model.RoleData;
import com.bernardomg.example.spring.security.mvc.domain.user.model.UserData;
import com.bernardomg.example.spring.security.mvc.domain.user.service.UserService;
import com.bernardomg.example.spring.security.mvc.test.configuration.annotation.IntegrationTest;

/**
 * Integration tests for the persistent user service, verifying that users can be read.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@IntegrationTest
@DisplayName("User service read operations")
public class ITUserServiceRead {

    /**
     * User service being tested.
     */
    @Autowired
    private UserService service;

    /**
     * Default constructor.
     */
    public ITUserServiceRead() {
        super();
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "READ_USER" })
    @DisplayName("Users can be read")
    @Sql("/db/populate/full.sql")
    public void testGetAllUsers() {
        final Iterable<UserData> users;

        users = service.getAllUsers();

        Assertions.assertEquals(6, IterableUtils.size(users));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "READ_USER" })
    @DisplayName("No users are returned when there are none in the DB")
    public void testGetAllUsers_Empty() {
        final Iterable<UserData> users;

        users = service.getAllUsers();

        Assertions.assertEquals(0, IterableUtils.size(users));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "READ_USER" })
    @DisplayName("Users and their privileges can be read")
    @Sql("/db/populate/full.sql")
    public void testGetAllUsers_Privileges() {
        final Iterable<UserData> users;
        final UserData           user;
        final RoleData           role;
        final PrivilegeData      privilege;

        users = service.getAllUsers();

        // Finds the admin
        user = StreamSupport.stream(users.spliterator(), false)
            .filter((u) -> "admin".equals(u.getUsername()))
            .findFirst()
            .get();

        Assertions.assertEquals("admin", user.getUsername());

        Assertions.assertEquals(1, IterableUtils.size(user.getRoles()));
        role = user.getRoles()
            .iterator()
            .next();
        Assertions.assertEquals("ADMIN", role.getName());

        Assertions.assertEquals(4, IterableUtils.size(role.getPrivileges()));
        privilege = role.getPrivileges()
            .iterator()
            .next();
        Assertions.assertEquals("CREATE_USER", privilege.getName());
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "READ_USER" })
    @DisplayName("A single user with no roles can be read")
    @Sql("/db/populate/full.sql")
    public void testGetUser_NoRoles() {
        final UserData user;

        user = service.getUser("noroles");

        Assertions.assertEquals("noroles", user.getUsername());
        Assertions.assertTrue(IterableUtils.isEmpty(user.getRoles()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "READ_USER" })
    @DisplayName("A single user with roles and no privileges can be read")
    @Sql("/db/populate/admin_roles_no_privileges.sql")
    public void testGetUser_Roles_NoPrivileges() {
        final UserData user;
        final RoleData role;

        user = service.getUser("admin");

        Assertions.assertEquals("admin", user.getUsername());

        Assertions.assertEquals(1, IterableUtils.size(user.getRoles()));
        role = user.getRoles()
            .iterator()
            .next();
        Assertions.assertEquals("ADMIN", role.getName());

        Assertions.assertEquals(0, IterableUtils.size(role.getPrivileges()));
    }

}
