
package com.bernardomg.example.spring.mvc.security.config;

import javax.servlet.Filter;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

@Configuration
public class AuthenticationConfig {

    public AuthenticationConfig() {
        super();
    }

    @Bean("jdbcTokenRepository")
    public PersistentTokenRepository
            getJdbcTokenRepository(final DataSource dataSource) {
        final JdbcTokenRepositoryImpl repo;

        repo = new JdbcTokenRepositoryImpl();

        repo.setDataSource(dataSource);

        return repo;
    }

    @Bean("passwordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("rememberMeFilter")
    public Filter getRememberMeFilter(
            final AuthenticationManager authenticationManager,
            final RememberMeServices rememberMeServices) {
        return new RememberMeAuthenticationFilter(authenticationManager,
                rememberMeServices);
    }

    @Bean("rememberMeServices")
    public RememberMeServices getRememberMeServices(
            @Value("${rememberme.key}") final String key,
            final UserDetailsService userDetailsService,
            final PersistentTokenRepository tokenRepository) {
        return new PersistentTokenBasedRememberMeServices(key,
                userDetailsService, tokenRepository);
    }

}
