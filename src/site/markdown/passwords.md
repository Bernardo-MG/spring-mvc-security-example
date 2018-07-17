# Passwords

Passwords are encrypted with the use of a PasswordEncoder.

This is required by the authentication manager:

```
<authentication-manager>
   <authentication-provider user-service-ref="userDetailsService">
      <password-encoder ref="passwordEncoder" />
   </authentication-provider>
</authentication-manager>
```

But it is also used to encode passwords received from the frontend, when creating or updating users.

