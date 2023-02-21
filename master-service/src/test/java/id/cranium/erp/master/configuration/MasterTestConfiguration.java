package id.cranium.erp.master.configuration;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.SimpleThreadScope;

@Configuration
public class MasterTestConfiguration {
    
    @Bean
    public CustomScopeConfigurer masterCustomScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope("request", new SimpleThreadScope());
        return configurer;
    }
}
