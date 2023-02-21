package id.cranium.erp.inventory.configuration;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceSlaveInventoryConfiguration {
    
    @Bean
    @ConfigurationProperties(prefix = "inventory.spring.slave.datasource")
    public DataSourceProperties inventorySlaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "inventory.spring.slave.datasource.hikari")
    public DataSource inventorySlaveDataSource() {
        return inventorySlaveDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }
}
