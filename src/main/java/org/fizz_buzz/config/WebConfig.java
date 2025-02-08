package org.fizz_buzz.config;

import org.fizz_buzz.controller.AuthenticationController;
import org.fizz_buzz.controller.MainPageController;
import org.fizz_buzz.controller.RegistrationController;
import org.fizz_buzz.controller.TestController;
import org.fizz_buzz.filter.AuthorizationFilter;
import org.fizz_buzz.repository.UserRepository;
import org.fizz_buzz.service.AuthenticationAndAuthorizationService;
import org.fizz_buzz.service.RegistrationService;
import org.fizz_buzz.test.TestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
//@Import({DataLayerConfig.class, ServiceConfig.class, ThymeleafConfig.class})
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
    public RegistrationController registrationController(@Autowired RegistrationService registrationService,
                                                         @Autowired AuthenticationAndAuthorizationService authenticationAndAuthorizationService) {
        return new RegistrationController(registrationService, authenticationAndAuthorizationService);
    }

    @Bean
    public AuthenticationController authenticationController(@Autowired AuthenticationAndAuthorizationService authenticationAndAuthorizationService){
        return new AuthenticationController(authenticationAndAuthorizationService);
    }

//    public AuthorizationFilter authorizationFilter(@Autowired AuthenticationAndAuthorizationService authenticationAndAuthorizationServic){
//        return  new AuthorizationFilter(authenticationAndAuthorizationServic);
//    }
}
