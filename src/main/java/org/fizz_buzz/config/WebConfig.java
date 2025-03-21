package org.fizz_buzz.config;

import org.fizz_buzz.controller.AuthenticationController;
import org.fizz_buzz.controller.GlobalExceptionHandler;
import org.fizz_buzz.controller.MainPageController;
import org.fizz_buzz.controller.RegistrationController;
import org.fizz_buzz.controller.TestController;
import org.fizz_buzz.controller.WeatherController;
import org.fizz_buzz.service.AuthenticationService;
import org.fizz_buzz.service.SessionService;
import org.fizz_buzz.service.UserService;
import org.fizz_buzz.service.WeatherService;
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
    public RegistrationController registrationController(AuthenticationService authenticationService) {
        return new RegistrationController(authenticationService);
    }

    @Bean
    public AuthenticationController authenticationController(AuthenticationService authenticationService){
        return new AuthenticationController(authenticationService);
    }

    @Bean
    public WeatherController weatherController(WeatherService weatherService,
                                               UserService userService,
                                               SessionService sessionService){
        return new WeatherController(weatherService, userService, sessionService);
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }

}
