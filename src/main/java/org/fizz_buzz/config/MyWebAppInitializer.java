package org.fizz_buzz.config;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.fizz_buzz.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

//public class MyWebAppInitializer implements WebApplicationInitializer {
//
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//
////        context.register(WebConfig.class);
//
////        servletContext
////                .addFilter("myFilter", new DelegatingFilterProxy("authorizationFilter", context))
////                .addMappingForUrlPatterns(null, false, "/*");
////        context.setServletContext(servletContext);
////         context.setServletContext(servletContext);
//    }
//
//
//}
