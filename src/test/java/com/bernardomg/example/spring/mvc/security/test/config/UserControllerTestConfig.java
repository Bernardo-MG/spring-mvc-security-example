
package com.bernardomg.example.spring.mvc.security.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ UserServiceTestConfig.class, ControllerTestConfig.class })
public class UserControllerTestConfig {

}
