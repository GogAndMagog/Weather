package org.fizz_buzz.config;

import org.fizz_buzz.repository.SessionRepository;
import org.fizz_buzz.repository.UserRepository;
import org.fizz_buzz.service.AuthenticationAndAuthorizationService;
import org.fizz_buzz.service.RegistrationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("org.fizz_buzz.filter")
public class ServiceConfig {

    @Value("${sessionDurationTime}")
    private long sessionDurationTime;

    @Bean
    public AuthenticationAndAuthorizationService authenticationAndAuthorizationService(SessionRepository sessionRepository,
                                                                                       UserRepository userRepository) {
        return new AuthenticationAndAuthorizationService(userRepository, sessionRepository);
    }

    @Bean
    public RegistrationService registrationService(UserRepository userRepository,
                                                   SessionRepository sessionRepository) {
        return new RegistrationService(userRepository, sessionRepository);
    }
}
