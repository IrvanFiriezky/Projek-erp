package id.cranium.erp.inventory.configuration;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

@Configuration
public class DataSourceInventoryConfiguration {
    
    @Bean
    @ConfigurationProperties(prefix = "inventory.spring.datasource")
    public DataSourceProperties inventoryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "inventory.spring.datasource.hikari")
    public DataSource inventoryDataSource() {
        return inventoryDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

    @Value("${inventory.spring.flyway.locations}")
    String flywayLocations;

    @Value("${inventory.spring.flyway.schemas}")
    String flywaySchema;

    @Bean(initMethod = "migrate")
    @FlywayDataSource
    public Flyway inventoryFlyway(@Qualifier("inventoryDataSource") DataSource dataSource) {
        return new Flyway(
            new FluentConfiguration()
            .locations(flywayLocations)
            .schemas(flywaySchema)
            //.outOfOrder(true)
            .dataSource(dataSource)
        );
    }
}
