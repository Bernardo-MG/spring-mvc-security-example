
package com.wandrell.example.spring.mvc.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wandrell.example.spring.mvc.security.auth.PersistentUserDetailsService;

@Service
public class AnnotatedRoleSecuredService implements RoleSecuredService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PersistentUserDetailsService.class);

    public AnnotatedRoleSecuredService() {
        super();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public void adminMethod() {
        final Authentication authentication;

        authentication = SecurityContextHolder.getContext().getAuthentication();

        LOGGER.info("Called method secured for admin");
        LOGGER.info("User authorities: {}", authentication.getAuthorities());
    }

}
