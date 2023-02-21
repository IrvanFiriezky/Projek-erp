package id.cranium.erp.starter.configuration;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration {
    
    @Bean
    @ConfigurationProperties(prefix = "starter.spring.datasource")
    public DataSourceProperties starterDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "starter.spring.datasource.hikari")
    public DataSource starterDataSource() {
        return starterDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

}
