package org.fizz_buzz.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class LiquibaseConfig {
    @Bean
    public SpringLiquibase liquibase(@Autowired DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setLiquibaseSchema("Main");
        liquibase.setDefaultSchema("Main");

        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.yaml");
        liquibase.setDataSource(dataSource);

        return liquibase;
    }
}
