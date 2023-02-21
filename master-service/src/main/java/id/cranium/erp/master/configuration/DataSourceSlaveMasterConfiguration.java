package id.cranium.erp.master.configuration;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceSlaveMasterConfiguration {
    
    @Bean
    @ConfigurationProperties(prefix = "master.spring.slave.datasource")
    public DataSourceProperties masterSlaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "master.spring.slave.datasource.hikari")
    public DataSource masterSlaveDataSource() {
        return masterSlaveDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }
}
