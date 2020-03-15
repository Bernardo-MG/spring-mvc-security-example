
package com.bernardomg.example.spring.mvc.security.config;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, mode = AdviceMode.ASPECTJ)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    public MethodSecurityConfig() {
        super();
    }

}
