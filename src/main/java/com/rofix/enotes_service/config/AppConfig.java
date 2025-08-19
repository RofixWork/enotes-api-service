package com.rofix.enotes_service.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AppConfig {
    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }

    @Bean
    public AuditorAware<Long> auditorAware()
    {
        return new AuditorAwareImpl();
    }

    @Bean
    public Validator validatorHandler()
    {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

}
