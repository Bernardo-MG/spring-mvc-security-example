
package com.wandrell.example.spring.mvc.security.controller.dto;

import javax.validation.constraints.NotNull;

public class LoginForm {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public LoginForm() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

}
