package id.cranium.erp.starter.configuration;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceSlaveConfiguration {
    
    @Bean
    @ConfigurationProperties(prefix = "starter.spring.slave.datasource")
    public DataSourceProperties starterSlaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "starter.spring.slave.datasource.hikari")
    public DataSource starterSlaveDataSource() {
        return starterSlaveDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

}
