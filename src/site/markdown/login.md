# Login

A login form is used to authenticate users. These are verified against the users stored in database. The PersistentUserDetailsService takes care of this.

## Login Form

The LoginFormController handles the frontend view, wich includes the long form. Data sent by this form will be handled by Spring.

The security.xml file defines all the configuration for this, including the login URL where the data will be posted.

```
<http>
   <form-login login-page="/login" login-processing-url="/login"
      default-target-url="/" authentication-failure-url="/login?error=true"
      always-use-default-target="true" />
</http>
```

The authentication manager will take care of the authentication procedure:

```
<authentication-manager>
   <authentication-provider user-service-ref="userDetailsService"/>
</authentication-manager>
```

The UserDetails service used is PersistentUserDetailsService.
