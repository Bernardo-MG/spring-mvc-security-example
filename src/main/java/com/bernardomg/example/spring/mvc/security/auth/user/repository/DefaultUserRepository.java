
package com.bernardomg.example.spring.mvc.security.auth.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.bernardomg.example.spring.mvc.security.auth.user.model.User;

import lombok.AllArgsConstructor;

/**
 * Default implementation of the user repository.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Repository
@AllArgsConstructor
public final class DefaultUserRepository implements UserRepository {

    /**
     * JDBC template for running queries.
     */
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Query for finding the privileges of a user.
     */
    private final String                     queryForUsername = "SELECT * FROM users u WHERE u.username = :username";

    @Override
    public final Optional<User> findOneByUsername(final String username) {
        final SqlParameterSource namedParameters;
        final List<User>         users;
        final Optional<User>     user;

        namedParameters = new MapSqlParameterSource().addValue("username", username);
        users = jdbcTemplate.query(queryForUsername, namedParameters, new UserRowMapper());

        if (users.isEmpty()) {
            user = Optional.empty();
        } else {
            user = Optional.ofNullable(users.iterator()
                .next());
        }

        return user;
    }

}
