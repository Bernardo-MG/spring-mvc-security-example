
package com.bernardomg.example.spring.mvc.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true,
        jsr250Enabled = true)
public class MethodSecurityConfig {

    public MethodSecurityConfig() {
        super();
    }

}
