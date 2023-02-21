package id.cranium.erp.master.configuration;

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
public class DataSourceMasterConfiguration {
    
    @Bean
    @ConfigurationProperties(prefix = "master.spring.datasource")
    public DataSourceProperties masterDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "master.spring.datasource.hikari")
    public DataSource masterDataSource() {
        return masterDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

    @Value("${master.spring.flyway.locations}")
    String flywayLocations;

    @Value("${master.spring.flyway.schemas}")
    String flywaySchema;

    @Bean(initMethod = "migrate")
    @FlywayDataSource
    public Flyway masterFlyway(@Qualifier("masterDataSource") DataSource dataSource) {
        return new Flyway(
            new FluentConfiguration()
            .locations(flywayLocations)
            .schemas(flywaySchema)
            //.outOfOrder(true)
            .dataSource(dataSource)
        );
    }
}
