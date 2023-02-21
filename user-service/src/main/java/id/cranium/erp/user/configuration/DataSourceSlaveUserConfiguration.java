package id.cranium.erp.user.configuration;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceSlaveUserConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "user.spring.slave.datasource")
    public DataSourceProperties userSlaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "user.spring.slave.datasource.hikari")
    public DataSource userSlaveDataSource() {
        return userSlaveDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }
}
