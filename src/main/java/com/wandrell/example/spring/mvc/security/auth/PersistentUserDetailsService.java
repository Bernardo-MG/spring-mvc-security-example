
package com.wandrell.example.spring.mvc.security.auth;

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wandrell.example.spring.mvc.security.persistence.repository.PersistentUserDetailsRepository;

@Service("userDetailsService")
public class PersistentUserDetailsService implements UserDetailsService {

    /**
     * Logger.
     */
    private static final Logger                   LOGGER = LoggerFactory
            .getLogger(PersistentUserDetailsService.class);

    private final PersistentUserDetailsRepository userRepo;

    @Autowired
    public PersistentUserDetailsService(
            final PersistentUserDetailsRepository userRepository) {
        super();

        userRepo = checkNotNull(userRepository,
                "Received a null pointer as repository");
    }

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        final UserDetails user;

        LOGGER.debug("Asked for username {}", username);

        user = getPersistentUserDetailsRepository().findOneByUsername(username);

        if (user == null) {
            LOGGER.debug("Username not found in DB");
            throw new UsernameNotFoundException(username);
        } else {
            LOGGER.debug("Username found in DB");
        }

        return user;
    }

    private final PersistentUserDetailsRepository
            getPersistentUserDetailsRepository() {
        return userRepo;
    }

}
