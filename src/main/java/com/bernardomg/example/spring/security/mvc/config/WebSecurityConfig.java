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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * User details service.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    public WebSecurityConfig() {
        super();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        final Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeRequestsCustomizer;
        final Customizer<FormLoginConfigurer<HttpSecurity>>                                                        formLoginCustomizer;
        final Customizer<LogoutConfigurer<HttpSecurity>>                                                           logoutCustomizer;
        final Customizer<RememberMeConfigurer<HttpSecurity>>                                                       rememberMeCustomizer;
        final Customizer<OAuth2LoginConfigurer<HttpSecurity>>                                                      oauth2LoginCustomizer;

        // Authorization
        authorizeRequestsCustomizer = getAuthorizeRequestsCustomizer();

        // Login form
        formLoginCustomizer = c -> c.loginPage("/login")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/", true)
            .failureUrl("/login?error=true");

        // Logout
        logoutCustomizer = c -> c.logoutUrl("/logout")
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/");

        // Remember me
        // One day
        rememberMeCustomizer = c -> c.tokenValiditySeconds(24 * 60 * 60);
        // OAUTH2
        oauth2LoginCustomizer = c -> c.loginPage("/login");

        http.authorizeHttpRequests(authorizeRequestsCustomizer)
            .formLogin(formLoginCustomizer)
            .logout(logoutCustomizer)
            .rememberMe(rememberMeCustomizer)
            .oauth2Login(oauth2LoginCustomizer);

        http.userDetailsService(userDetailsService);

        return http.build();
    }

    /**
     * Returns the request authorisation configuration.
     *
     * @return the request authorisation configuration
     */
    private final Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>
            getAuthorizeRequestsCustomizer() {
        return c -> c.requestMatchers("/static/**", "/login*")
            .permitAll()
            .anyRequest()
            .authenticated();
    }

}
