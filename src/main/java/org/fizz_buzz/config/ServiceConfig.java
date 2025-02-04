package org.fizz_buzz.config;

import org.fizz_buzz.repository.SessionRepository;
import org.fizz_buzz.repository.UserRepository;
import org.fizz_buzz.service.AuthenticationAndAuthorizationService;
import org.fizz_buzz.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ServiceConfig {

    @Value("${sessionDurationTime}")
    private long sessionDurationTime = 59000;

    @Bean
    public AuthenticationAndAuthorizationService authenticationAndAuthorizationService(@Autowired SessionRepository sessionRepository,
                                                                                       @Autowired UserRepository userRepository) {
        return new AuthenticationAndAuthorizationService(userRepository, sessionRepository, sessionDurationTime);
    }

    @Bean
    public RegistrationService registrationService(@Autowired UserRepository userRepository,
                                                   @Autowired SessionRepository sessionRepository) {
        return new RegistrationService(userRepository, sessionRepository, sessionDurationTime);
    }
}
