package org.fizz_buzz.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
@Profile("Develop")
public class DataSourceConfig {

    @Value("${user}")
    private String username;
    @Value("${pass}")
    private String password;
    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.schema}")
    private String schema;
    @Value("${driver}")
    private String driver;

    @Bean
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setJdbcUrl(dbUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setSchema(schema);
        return new HikariDataSource(config);
    }

}
