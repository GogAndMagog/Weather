package org.fizz_buzz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("Develop")
@Import({DataSourceConfig.class, JPAConfig.class})
public class DataLayerConfig {
}
