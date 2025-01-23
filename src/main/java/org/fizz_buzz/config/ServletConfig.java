package org.fizz_buzz.config;

import org.fizz_buzz.controller.TestController;
import org.fizz_buzz.test.TestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ServletConfig {

    @Bean
    public TestController testController(@Autowired TestBean testBean) {
        return new TestController(testBean);
    }
}
