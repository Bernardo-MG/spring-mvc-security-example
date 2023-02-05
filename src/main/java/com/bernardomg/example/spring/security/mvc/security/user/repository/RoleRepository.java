/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.example.spring.security.mvc.security.user.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.example.spring.security.mvc.security.user.model.PersistentRole;

/**
 * Repository for users.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface RoleRepository extends JpaRepository<PersistentRole, Long> {

    /**
     * Returns the roles with the name received.
     *
     * @param name
     *            name of the role
     * @return roles names in the input
     */
    public Optional<PersistentRole> findByName(final String name);

    /**
     * Returns all the roles with one of the names received.
     *
     * @param names
     *            names of the roles
     * @return roles names in the input
     */
    public Collection<PersistentRole> findByNameIn(final Collection<String> names);

    /**
     * Returns all the roles for a user. This requires a join from the user up to the roles.
     *
     * @param id
     *            user id
     * @return all the privileges for the user
     */
    @Query(value = "SELECT r.* FROM roles r JOIN user_roles ur ON r.id = ur.role_id JOIN users u ON u.id = ur.user_id WHERE u.id = :id",
            nativeQuery = true)
    public Collection<PersistentRole> findForUser(@Param("id") final Long id);

    /**
     * Registers a role for the specified user. This will update the relationship table for user roles.
     *
     * @param userId
     *            id for the user to receive the role
     * @param roleId
     *            id of the role for the user
     */
    @Modifying
    @Query(value = "INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
    public void registerForUser(@Param("userId") final Long userId, @Param("roleId") final Long roleId);

}
