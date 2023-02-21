package id.cranium.erp.starter.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
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
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "id.cranium.erp.starter.repository.primary", 
    entityManagerFactoryRef = "starterEntityManagerFactory", 
    transactionManagerRef = "starterTransactionManager",
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@Profile("!tc")
public class JpaConfiguration {
    
    @Primary
    @Bean
    @ConfigurationProperties("starter.spring.jpa")
    public JpaProperties starterJpaProperties() {
        return new JpaProperties();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean starterEntityManagerFactory(@Qualifier("starterDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder, @Qualifier("starterJpaProperties") JpaProperties jpaProperties) {
        return builder
          .dataSource(dataSource)
          .packages("id.cranium.erp.starter.entity")
          .properties(jpaProperties.getProperties())
          .build();
    }

    @Bean
    public PlatformTransactionManager starterTransactionManager(@Qualifier("starterEntityManagerFactory") LocalContainerEntityManagerFactoryBean starterEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(starterEntityManagerFactory.getObject()));
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new AuditorAwareImpl();
    }

}
