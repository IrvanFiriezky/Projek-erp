package id.cranium.erp.starter.configurationdto;

import org.springframework.core.annotation.Order;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageSourceStarterConfiguration {
    
    @Value("${starterdto.spring.messages.basename}")
    private String starterDtoSpringMessagesBasename;

    @Value("${starterdto.spring.messages.encoding}")
    private String starterDtoSpringMessagesEncoding;
    
    @Bean
    public MessageSource starterDtoMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(starterDtoSpringMessagesBasename);
        messageSource.setDefaultEncoding(starterDtoSpringMessagesEncoding);
        return messageSource;
    }

    @Bean
    @Order(1)
    public LocalValidatorFactoryBean getValidatorStarterDto() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(starterDtoMessageSource());
        return bean;
    }

}
