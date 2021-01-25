package com.enzo.auditors;


import com.enzo.entities.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class PersistenceContext {

    @Bean
    AuditorAware<UserEntity> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }

}
