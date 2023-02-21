package id.cranium.erp.user.configuration;

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
public class DataSourceUserConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "user.spring.datasource")
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "user.spring.datasource.hikari")
    public DataSource userDataSource() {
        return userDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

    @Value("${user.spring.flyway.locations}")
    String flywayLocations;

    @Value("${user.spring.flyway.schemas}")
    String flywaySchema;

    @Bean(initMethod = "migrate")
    @FlywayDataSource
    public Flyway userFlyway(@Qualifier("userDataSource") DataSource dataSource) {
        return new Flyway(
            new FluentConfiguration()
            .locations(flywayLocations)
            .schemas(flywaySchema)
            //.outOfOrder(true)
            .dataSource(dataSource)
        );
    }
}
