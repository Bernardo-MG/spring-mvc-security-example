
package com.wandrell.example.spring.mvc.security.controller.dto;

public class LoginForm {

    private String email;

    private String password;

    public LoginForm() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

}
