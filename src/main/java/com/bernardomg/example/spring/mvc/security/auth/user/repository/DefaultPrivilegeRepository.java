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

import java.util.Collection;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.bernardomg.example.spring.mvc.security.auth.user.model.Privilege;

import lombok.AllArgsConstructor;

/**
 * Default implementation of the privilege repository.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Repository
@AllArgsConstructor
public final class DefaultPrivilegeRepository implements PrivilegeRepository {

    /**
     * JDBC template for running queries.
     */
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Query for finding the privileges of a user.
     */
    private final String                     queryForUser = "SELECT p.* FROM privileges p JOIN role_privileges rp ON p.id = rp.privilege_id JOIN roles r ON r.id = rp.role_id JOIN USER_ROLES ur ON r.id = ur.role_id JOIN users u ON u.id = ur.user_id WHERE u.id = :id";

    @Override
    public final Collection<Privilege> findForUser(final Long id) {
        final SqlParameterSource namedParameters;

        namedParameters = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.query(queryForUser, namedParameters, new PrivilegeRowMapper());
    }

}
