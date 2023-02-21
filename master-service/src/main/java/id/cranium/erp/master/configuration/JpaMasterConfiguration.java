package id.cranium.erp.master.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
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
@EnableJpaRepositories(basePackages = "id.cranium.erp.master.repository.primary", 
    entityManagerFactoryRef = "masterEntityManagerFactory", 
    transactionManagerRef = "masterTransactionManager",
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@Profile("!tc")
public class JpaMasterConfiguration {

    @Bean
    @ConfigurationProperties("master.spring.jpa")
    public JpaProperties masterJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(@Qualifier("masterDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder, @Qualifier("masterJpaProperties") JpaProperties jpaProperties) {
        return builder
          .dataSource(dataSource)
          .packages("id.cranium.erp.master.entity")
          .properties(jpaProperties.getProperties())
          .build();
    }

    @Bean
    public PlatformTransactionManager masterTransactionManager(@Qualifier("masterEntityManagerFactory") LocalContainerEntityManagerFactoryBean masterEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(masterEntityManagerFactory.getObject()));
    }

}