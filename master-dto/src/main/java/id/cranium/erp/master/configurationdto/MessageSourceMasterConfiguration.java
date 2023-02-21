package id.cranium.erp.master.configurationdto;

import org.springframework.core.annotation.Order;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageSourceMasterConfiguration {
    
    @Value("${masterdto.spring.messages.basename}")
    private String masterDtoSpringMessagesBasename;

    @Value("${masterdto.spring.messages.encoding}")
    private String masterDtoSpringMessagesEncoding;
    
    @Bean
    public MessageSource masterDtoMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(masterDtoSpringMessagesBasename);
        messageSource.setDefaultEncoding(masterDtoSpringMessagesEncoding);
        return messageSource;
    }

    @Bean
    @Order(2)
    public LocalValidatorFactoryBean getValidatorMasterDto() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(masterDtoMessageSource());
        return bean;
    }

}
