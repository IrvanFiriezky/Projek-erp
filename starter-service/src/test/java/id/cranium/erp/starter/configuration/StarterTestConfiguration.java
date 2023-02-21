package id.cranium.erp.starter.configuration;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.SimpleThreadScope;

@Configuration
public class StarterTestConfiguration {
    
    @Bean
    public CustomScopeConfigurer starterCustomScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope("request", new SimpleThreadScope());
        return configurer;
    }
}
