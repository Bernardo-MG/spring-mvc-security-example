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

package com.bernardomg.example.spring.mvc.security.auth.user.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bernardomg.example.spring.mvc.security.auth.user.model.PersistentRole;

/**
 * Repository for users.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface RoleRepository extends CrudRepository<PersistentRole, Long> {

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
    public Iterable<PersistentRole> findByNameIn(final Iterable<String> names);

    /**
     * Returns all the roles for a user. This requires a join from the user up to the roles.
     *
     * @param id
     *            user id
     * @return all the privileges for the user
     */
    @Query("SELECT r.* FROM roles r ON r.id = rp.role_id JOIN USER_ROLES ur ON r.id = ur.role_id JOIN users u ON u.id = ur.user_id WHERE u.id = :id")
    public Iterable<PersistentRole> findForUser(@Param("id") final Long id);

}