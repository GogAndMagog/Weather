package org.fizz_buzz.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@PropertySource("classpath:db.properties")
@Profile("Develop")
public class DataSourceConfig {

    @Value("${POSTGRES_USER}")
    private String username;
    @Value("${POSTGRES_PASS}")
    private String password;
    @Value("${db.url}")
    private String dbUrl;
    @Value("${driver}")
    private String driver;

    @Bean
    public DataSource getDataSource() {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(dbUrl);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }

}
