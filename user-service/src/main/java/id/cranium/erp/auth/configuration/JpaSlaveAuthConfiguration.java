package id.cranium.erp.auth.configuration;

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
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableEnversRepositories
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "id.cranium.erp.auth.repository.slave", 
    entityManagerFactoryRef = "authEntityManagerFactory", 
    transactionManagerRef = "authTransactionManager",
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@Profile("!tc")
public class JpaSlaveAuthConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean authSlaveEntityManagerFactory(@Qualifier("authSlaveDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder, @Qualifier("authJpaProperties") JpaProperties jpaProperties) {
        return builder
          .dataSource(dataSource)
          .packages("id.cranium.erp.auth.entity", "id.cranium.erp.user.entity")
          .properties(jpaProperties.getProperties())
          .build();
    }

    @Bean
    public PlatformTransactionManager authSlaveTransactionManager(@Qualifier("authSlaveEntityManagerFactory") LocalContainerEntityManagerFactoryBean authSlaveEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(authSlaveEntityManagerFactory.getObject()));
    }

}