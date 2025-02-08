package org.fizz_buzz.initializer;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import org.fizz_buzz.config.DataLayerConfig;
import org.fizz_buzz.config.JPATestConfig;
import org.fizz_buzz.config.RootConfig;
import org.fizz_buzz.config.ServiceConfig;
import org.fizz_buzz.config.ThymeleafConfig;
import org.fizz_buzz.config.WebConfig;
import org.fizz_buzz.filter.AuthorizationFilter;
import org.fizz_buzz.filter.OncePerRequestFilterTest;
import org.fizz_buzz.service.AuthenticationAndAuthorizationService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {

        return new Class[]{JPATestConfig.class, ServiceConfig.class, ThymeleafConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

//    @Override
//    protected Filter[] getServletFilters() {
//        return new Filter[] {(new OncePerRequestFilterTest())};
//    }
}
