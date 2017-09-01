
package com.wandrell.example.spring.mvc.security.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wandrell.example.spring.mvc.security.persistence.model.PersistentUserDetails;

public interface PersistentUserDetailsRepository
        extends JpaRepository<PersistentUserDetails, Long> {

    public PersistentUserDetails findOneByUsername(final String username);

}
