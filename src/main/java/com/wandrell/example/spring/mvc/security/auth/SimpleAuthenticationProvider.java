
package com.wandrell.example.spring.mvc.security.auth;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class SimpleAuthenticationProvider implements AuthenticationProvider {

    /**
     * Logger for the service.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SimpleAuthenticationProvider.class);

    @Override
    public Authentication authenticate(final Authentication authentication)
            throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();

        LOGGER.warn("{} - {}", name, password);

        return new UsernamePasswordAuthenticationToken(name, password,
                new ArrayList<>());
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
