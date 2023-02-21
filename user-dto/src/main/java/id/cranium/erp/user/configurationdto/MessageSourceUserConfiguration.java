package id.cranium.erp.user.configurationdto;

import org.springframework.core.annotation.Order;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageSourceUserConfiguration {
    
    @Value("${userdto.spring.messages.basename}")
    private String userDtoSpringMessagesBasename;

    @Value("${userdto.spring.messages.encoding}")
    private String userDtoSpringMessagesEncoding;
    
    @Bean
    public MessageSource userDtoMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(userDtoSpringMessagesBasename);
        messageSource.setDefaultEncoding(userDtoSpringMessagesEncoding);
        return messageSource;
    }

    @Bean
    @Order(2)
    public LocalValidatorFactoryBean getValidatorUserDto() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(userDtoMessageSource());
        return bean;
    }

}
