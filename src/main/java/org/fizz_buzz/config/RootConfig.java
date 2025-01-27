package org.fizz_buzz.config;

import org.fizz_buzz.repository.UserRepository;
import org.fizz_buzz.test.TestBean;
import org.fizz_buzz.test.TestPrototypeBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
public class RootConfig {
    @Bean
    public TestBean testBean() {
        return new TestBean();
    }

    @Bean
    @Scope("prototype")
    public TestPrototypeBean testPrototypeBean() {
        return new TestPrototypeBean();
    }
}
