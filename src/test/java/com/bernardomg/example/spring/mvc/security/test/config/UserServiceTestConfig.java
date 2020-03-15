
package com.bernardomg.example.spring.mvc.security.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bernardomg.example.spring.mvc.security.config.AuthenticationConfig;
import com.bernardomg.example.spring.mvc.security.config.SecurityConfig;

@Configuration
@Import({ PersistenceTestConfig.class, SecurityConfig.class,
        AuthenticationConfig.class })
@ComponentScan({ "com.bernardomg.example.spring.mvc.security.auth.service",
        "com.bernardomg.example.spring.mvc.security.user.service" })
public class UserServiceTestConfig {

}
