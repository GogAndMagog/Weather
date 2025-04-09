package org.fizz_buzz.initializer;

import jakarta.servlet.annotation.WebListener;
import org.fizz_buzz.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@WebListener
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {

        return new Class[]{};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {

        return new Class[]{ WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {

        return new String[] { "/" };
    }

}
