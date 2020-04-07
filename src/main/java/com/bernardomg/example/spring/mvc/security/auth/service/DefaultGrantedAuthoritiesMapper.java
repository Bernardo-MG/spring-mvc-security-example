
package com.bernardomg.example.spring.mvc.security.auth.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;

import com.bernardomg.example.spring.mvc.security.config.SecurityConfig;

public final class DefaultGrantedAuthoritiesMapper
        implements GrantedAuthoritiesMapper {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultGrantedAuthoritiesMapper.class);

    public DefaultGrantedAuthoritiesMapper() {
        super();
    }

    @Override
    public final Collection<? extends GrantedAuthority> mapAuthorities(
            final Collection<? extends GrantedAuthority> authorities) {
        final Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

        authorities.forEach(authority -> {
            if (OidcUserAuthority.class.isInstance(authority)) {
                final OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;

                final OidcIdToken idToken = oidcUserAuthority.getIdToken();
                final OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

                // Map the claims found in idToken and/or userInfo
                // to one or more GrantedAuthority's and add it to
                // mappedAuthorities

            } else if (OAuth2UserAuthority.class.isInstance(authority)) {
                final OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;

                final Map<String, Object> userAttributes = oauth2UserAuthority
                        .getAttributes();

                // Map the attributes found in userAttributes
                // to one or more GrantedAuthority's and add it to
                // mappedAuthorities

            }
        });

        mappedAuthorities
                .addAll(AuthorityUtils.createAuthorityList("READ_USER"));

        LOGGER.debug("Got authorities {}", authorities);
        LOGGER.debug("Mapped authorities {}", mappedAuthorities);

        return mappedAuthorities;
    }

}
