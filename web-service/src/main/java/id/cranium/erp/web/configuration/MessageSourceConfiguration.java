package id.cranium.erp.web.configuration;

import org.springframework.core.annotation.Order;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageSourceConfiguration {

    @Value("${spring.messages.basename}")
    private String springMessagesBasename;

    @Value("${spring.messages.encoding}")
    private String springMessagesEncoding;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(springMessagesBasename.split(","));
        messageSource.setDefaultEncoding(springMessagesEncoding);
        return messageSource;
    }

    @Bean
    @Order(3)
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

}
