package org.fizz_buzz.initializer;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.fizz_buzz.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

public class ApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(WebConfig.class);

        FilterRegistration.Dynamic exceptionHandlerFilter = servletContext.addFilter("exceptionHandlerFilter",
                new DelegatingFilterProxy("exceptionHandlerFilter"));
        exceptionHandlerFilter.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic authFilter = servletContext.addFilter("authenticationFilter",
                new DelegatingFilterProxy("authenticationFilter"));
        authFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
