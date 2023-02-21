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
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableEnversRepositories
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "id.cranium.erp.master.repository.slave", 
    entityManagerFactoryRef = "masterSlaveEntityManagerFactory", 
    transactionManagerRef = "masterSlaveTransactionManager",
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@Profile("!tc")
public class JpaSlaveMasterConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean masterSlaveEntityManagerFactory(@Qualifier("masterSlaveDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder, @Qualifier("masterJpaProperties") JpaProperties jpaProperties) {
        return builder
          .dataSource(dataSource)
          .packages("id.cranium.erp.master.entity")
          .properties(jpaProperties.getProperties())
          .build();
    }

    @Bean
    public PlatformTransactionManager masterSlaveTransactionManager(@Qualifier("masterSlaveEntityManagerFactory") LocalContainerEntityManagerFactoryBean masterSlaveEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(masterSlaveEntityManagerFactory.getObject()));
    }

}