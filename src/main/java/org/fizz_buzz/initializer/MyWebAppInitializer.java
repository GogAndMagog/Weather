package org.fizz_buzz.initializer;

import org.fizz_buzz.config.DataLayerConfig;
import org.fizz_buzz.config.DataLayerTestConfig;
import org.fizz_buzz.config.ServiceConfig;
import org.fizz_buzz.config.ThymeleafConfig;
import org.fizz_buzz.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {

        return new Class[]{DataLayerConfig.class,
                DataLayerTestConfig.class,
                ServiceConfig.class,
                ThymeleafConfig.class};
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
