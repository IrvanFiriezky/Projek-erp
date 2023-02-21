package id.cranium.erp.auth.configuration;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceAuthConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "user.spring.datasource")
    public DataSourceProperties authDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "user.spring.datasource.hikari")
    public DataSource authDataSource() {
        return authDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }
    
}
