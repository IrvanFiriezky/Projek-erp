package id.cranium.erp.cron.configuration;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceSlaveCronConfiguration {
    
    @Bean
    @ConfigurationProperties(prefix = "cron.spring.slave.datasource")
    public DataSourceProperties cronSlaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "cron.spring.slave.datasource.hikari")
    public DataSource cronSlaveDataSource() {
        return cronSlaveDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

}
