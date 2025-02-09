package org.fizz_buzz.config;

import org.fizz_buzz.controller.AuthenticationController;
import org.fizz_buzz.controller.MainPageController;
import org.fizz_buzz.controller.RegistrationController;
import org.fizz_buzz.controller.TestController;
import org.fizz_buzz.service.AuthenticationAndAuthorizationService;
import org.fizz_buzz.service.RegistrationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebConfig {

    @Bean
    public MainPageController mainPageController(){
        return new MainPageController();
    }

    @Bean
    public TestController testController() {
        return new TestController();
    }

    @Bean
    public RegistrationController registrationController(RegistrationService registrationService,
                                                         AuthenticationAndAuthorizationService authenticationAndAuthorizationService) {
        return new RegistrationController(registrationService, authenticationAndAuthorizationService);
    }

    @Bean
    public AuthenticationController authenticationController(AuthenticationAndAuthorizationService authenticationAndAuthorizationService){
        return new AuthenticationController(authenticationAndAuthorizationService);
    }
}
