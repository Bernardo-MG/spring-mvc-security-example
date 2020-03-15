
package com.bernardomg.example.spring.mvc.security.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bernardomg.example.spring.mvc.security.config.AuthenticationConfig;
import com.bernardomg.example.spring.mvc.security.config.SecurityConfig;

@Configuration
@Import({ UserServiceTestConfig.class, PersistenceTestConfig.class,
        SecurityConfig.class, ControllerTestConfig.class,
        AuthenticationConfig.class })
public class SecuredControllerTestConfig {

}
