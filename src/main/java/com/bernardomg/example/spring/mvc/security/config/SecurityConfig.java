
package com.bernardomg.example.spring.mvc.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    public SecurityConfig() {
        super();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        final Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> authorizeRequestsCustomizer;
        final Customizer<FormLoginConfigurer<HttpSecurity>> formLoginCustomizer;
        final Customizer<LogoutConfigurer<HttpSecurity>> logoutCustomizer;
        final Customizer<RememberMeConfigurer<HttpSecurity>> rememberMeCustomizer;
        final Customizer<OAuth2LoginConfigurer<HttpSecurity>> oauth2LoginCustomizer;

        // Authorization
        authorizeRequestsCustomizer = c -> c
                .antMatchers("/static/**", "/login*").permitAll().anyRequest()
                .authenticated();
        // Login form
        formLoginCustomizer = c -> c.loginPage("/login")
                .loginProcessingUrl("/login").defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true");
        // Logout
        logoutCustomizer = c -> c.logoutUrl("/logout")
                .deleteCookies("JSESSIONID").logoutSuccessUrl("/");
        // Remember me
        rememberMeCustomizer = c -> c.tokenValiditySeconds(86400);
        // OAUTH2
        oauth2LoginCustomizer = c -> c.loginPage("/login");

        http.authorizeRequests(authorizeRequestsCustomizer)
                .formLogin(formLoginCustomizer).logout(logoutCustomizer)
                .rememberMe(rememberMeCustomizer)
                .oauth2Login(oauth2LoginCustomizer);
    }

}
