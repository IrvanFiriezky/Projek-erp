package id.cranium.erp.cron.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableEnversRepositories
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "id.cranium.erp.cron.repository.primary", 
    entityManagerFactoryRef = "cronEntityManagerFactory", 
    transactionManagerRef = "cronTransactionManager",
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@Profile("!tc")
public class JpaCronConfiguration {
    
    @Bean
    @ConfigurationProperties("cron.spring.jpa")
    public JpaProperties cronJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean cronEntityManagerFactory(@Qualifier("cronDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder, @Qualifier("cronJpaProperties") JpaProperties jpaProperties) {
        return builder
          .dataSource(dataSource)
          .packages("id.cranium.erp.cron.entity")
          .properties(jpaProperties.getProperties())
          .build();
    }

    @Bean
    public PlatformTransactionManager cronTransactionManager(@Qualifier("cronEntityManagerFactory") LocalContainerEntityManagerFactoryBean cronEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(cronEntityManagerFactory.getObject()));
    }

}
