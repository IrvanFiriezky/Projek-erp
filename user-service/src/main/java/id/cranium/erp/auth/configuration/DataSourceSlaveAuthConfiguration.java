package id.cranium.erp.auth.configuration;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceSlaveAuthConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "user.spring.slave.datasource")
    public DataSourceProperties authSlaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "user.spring.slave.datasource.hikari")
    public DataSource authSlaveDataSource() {
        return authSlaveDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }
    
}
