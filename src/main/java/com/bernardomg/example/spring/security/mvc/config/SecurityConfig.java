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

package com.bernardomg.example.spring.security.mvc.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

import com.bernardomg.example.spring.security.mvc.security.oauth.RegisterOAuth2UserService;
import com.bernardomg.example.spring.security.mvc.security.property.RememberMeProperties;
import com.bernardomg.example.spring.security.mvc.security.user.repository.PrivilegeRepository;
import com.bernardomg.example.spring.security.mvc.security.user.repository.RoleRepository;
import com.bernardomg.example.spring.security.mvc.security.user.repository.UserRepository;
import com.bernardomg.example.spring.security.mvc.security.userdetails.PersistentUserDetailsService;

import jakarta.servlet.Filter;

/**
 * Authentication configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    public SecurityConfig() {
        super();
    }

    @Bean("authenticationManagerFactoryBean")
    public AuthenticationManagerFactoryBean getAuthenticationManagerFactoryBean() {
        return new AuthenticationManagerFactoryBean();
    }

    @Bean("jdbcTokenRepository")
    public PersistentTokenRepository getJdbcTokenRepository(final DataSource dataSource) {
        final JdbcTokenRepositoryImpl repo;

        repo = new JdbcTokenRepositoryImpl();

        repo.setDataSource(dataSource);

        return repo;
    }

    @Bean("oAuth2UserService")
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> getOAuth2UserService(final UserRepository userRepo,
            final RoleRepository roleRepo, final PrivilegeRepository privilegeRepo) {
        return new RegisterOAuth2UserService(userRepo, roleRepo, privilegeRepo);
    }

    @Bean("passwordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("rememberMeFilter")
    public Filter getRememberMeFilter(final AuthenticationManager authenticationManager,
            final RememberMeServices rememberMeServices) {
        return new RememberMeAuthenticationFilter(authenticationManager, rememberMeServices);
    }

    @Bean("rememberMeServices")
    public RememberMeServices getRememberMeServices(final RememberMeProperties properties,
            final UserDetailsService userDetailsService, final PersistentTokenRepository tokenRepository) {
        return new PersistentTokenBasedRememberMeServices(properties.getKey(), userDetailsService, tokenRepository);
    }

    @Bean("userDetailsService")
    public UserDetailsService getUserDetailsService(final UserRepository userRepository,
            final PrivilegeRepository privilegeRepository) {
        return new PersistentUserDetailsService(userRepository, privilegeRepository);
    }

}
