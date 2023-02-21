package id.cranium.erp.cron.configuration;

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
public class DataSourceCronConfiguration {
    
    @Bean
    @ConfigurationProperties(prefix = "cron.spring.datasource")
    public DataSourceProperties cronDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "cron.spring.datasource.hikari")
    public DataSource cronDataSource() {
        return cronDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

    @Value("${cron.spring.flyway.locations}")
    String flywayLocations;

    @Value("${cron.spring.flyway.schemas}")
    String flywaySchema;

    @Bean(initMethod = "migrate")
    @FlywayDataSource
    public Flyway cronFlyway(@Qualifier("cronDataSource") DataSource dataSource) {
        return new Flyway(
            new FluentConfiguration()
            .locations(flywayLocations)
            .schemas(flywaySchema)
            //.outOfOrder(true)
            .dataSource(dataSource)
        );
    }

}
