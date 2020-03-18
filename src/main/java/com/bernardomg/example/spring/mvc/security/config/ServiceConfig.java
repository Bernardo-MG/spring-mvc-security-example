
package com.bernardomg.example.spring.mvc.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.example.spring.mvc.security.user.repository.PersistentRoleRepository;
import com.bernardomg.example.spring.mvc.security.user.repository.PersistentUserRepository;
import com.bernardomg.example.spring.mvc.security.user.service.DefaultUserService;
import com.bernardomg.example.spring.mvc.security.user.service.UserService;

@Configuration
public class ServiceConfig {

    public ServiceConfig() {
        super();
    }

    @Bean("userService")
    public UserService getUserService(final PersistentUserRepository userRepo,
            final PersistentRoleRepository roleRepo,
            final PasswordEncoder passEncoder) {
        return new DefaultUserService(userRepo, roleRepo, passEncoder);
    }

}
