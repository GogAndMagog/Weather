package org.fizz_buzz.config;

import org.fizz_buzz.controller.AuthenticationController;
import org.fizz_buzz.controller.FilterErrorController;
import org.fizz_buzz.controller.GlobalExceptionHandler;
import org.fizz_buzz.controller.RegistrationController;
import org.fizz_buzz.controller.WeatherController;
import org.fizz_buzz.interceptor.UserLoginInterceptor;
import org.fizz_buzz.repository.LocationRepository;
import org.fizz_buzz.service.AuthenticationService;
import org.fizz_buzz.service.SessionService;
import org.fizz_buzz.service.UserService;
import org.fizz_buzz.service.WeatherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = {"org.fizz_buzz.filter", "org.fizz_buzz.util"},
        basePackageClasses = {DataLayerConfig.class,
                DataLayerTestConfig.class,
                DataSourceConfig.class,
                JPAConfig.class,
                LiquibaseConfig.class,
                ServiceConfig.class,
                ThymeleafConfig.class
        })

@EnableSpringDataWebSupport
@EnableScheduling
public class WebConfig implements WebMvcConfigurer {

     @Bean
    public RegistrationController registrationController(AuthenticationService authenticationService) {
        return new RegistrationController(authenticationService);
    }

    @Bean
    public AuthenticationController authenticationController(AuthenticationService authenticationService) {
        return new AuthenticationController(authenticationService);
    }

    @Bean
    public WeatherController weatherController(WeatherService weatherService,
                                               UserService userService,
                                               SessionService sessionService,
                                               LocationRepository locationRepository) {
        return new WeatherController(weatherService, userService, sessionService, locationRepository);
    }

    @Bean
    public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver(){
        return new ExceptionHandlerExceptionResolver();
    }

    @Bean
    public FilterErrorController filterErrorController(){
        return new FilterErrorController();
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public UserLoginInterceptor userLoginInterceptor() {
        return new UserLoginInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**", "/js/**", "/images/**")
                .addResourceLocations("classpath:/static/css", "classpath:/static/js/", "classpath:/static/images/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor())
                .addPathPatterns("/register",
                        "/authenticate",
                        "/weather",
                        "/locations");
    }
}
