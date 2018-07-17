# Remember Me

The application is prepared to remember those users who choose so. This allows re-login automatically, even after the session has expired.

This requires generating a token and cookie to identify the user. This token is stored in the database, inside the PERSISTENT_LOGINS table.

## Configuration

```
<http>
   <remember-me data-source-ref="dataSource" token-validity-seconds="86400" />
</http>
```

## Beans

```
<bean id="rememberMeFilter" class="${rememberme.filter.bean}">
   <constructor-arg ref="authenticationManager" />
   <constructor-arg ref="rememberMeServices" />
</bean>

<bean id="rememberMeServices" class="${rememberme.services.bean}">
   <constructor-arg value="${rememberme.key}" />
   <constructor-arg ref="jdbcTokenRepository" />
   <constructor-arg ref="userDetailsService" />
</bean>

<bean id="jdbcTokenRepository" class="${rememberme.token.repo.bean}">
   <property name="dataSource" ref="dataSource" />
</bean>
```

## Form

The form includes an option for activating the remember-me functionality:

```
<div class="form-group">
   <div class="checkbox col-md-offset-2">
      <label> <input type="checkbox" name="remember-me"
         id="remember-me">Remember me
      </label>
   </div>
</div>
```

Spring will handle this parameter.
