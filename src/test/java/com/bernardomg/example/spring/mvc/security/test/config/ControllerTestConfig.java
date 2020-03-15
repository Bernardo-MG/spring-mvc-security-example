
package com.bernardomg.example.spring.mvc.security.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.bernardomg.example.spring.mvc.security.**.controller" })
public class ControllerTestConfig {

    public ControllerTestConfig() {
        super();
    }

}
