package org.fizz_buzz.config;

import org.fizz_buzz.repository.LocationRepository;
import org.fizz_buzz.repository.SessionRepository;
import org.fizz_buzz.repository.UserRepository;
import org.fizz_buzz.service.AuthenticationService;
import org.fizz_buzz.service.RegistrationService;
import org.fizz_buzz.service.SessionService;
import org.fizz_buzz.service.UserService;
import org.fizz_buzz.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.http.HttpClient;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("org.fizz_buzz.filter")
public class ServiceConfig {

    @Value("${sessionDurationTime}")
    private long sessionDurationTime;

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    public AuthenticationService authenticationAndAuthorizationService(SessionRepository sessionRepository,
                                                                       UserRepository userRepository,
                                                                       UserService userService,
                                                                       SessionService sessionService) {
        return new AuthenticationService(userRepository, sessionRepository, userService, sessionService);
    }

    @Bean
    public RegistrationService registrationService(UserRepository userRepository,
                                                   SessionRepository sessionRepository) {
        return new RegistrationService(userRepository, sessionRepository);
    }

    @Bean
    public UserService userService(LocationRepository locationRepository, UserRepository userRepository) {
        return new UserService(userRepository, locationRepository);
    }

    @Bean
    public SessionService sessionService(SessionRepository sessionRepository) {
        return new SessionService(sessionRepository);
    }

    @Bean
    public WeatherService weatherService(HttpClient httpClient) {
        return new WeatherService(httpClient);
    }

}
