package com.enzo.auditors;

import com.enzo.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<UserEntity> {

    @Autowired
    UserInterface userInterface;


    @Override
    public Optional<UserEntity> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return Optional.ofNullable(userInterface.findByEmail(authentication.getName()));

    }

}
