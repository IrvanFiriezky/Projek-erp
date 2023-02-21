package id.cranium.erp.inventory.configurationdto;

import org.springframework.core.annotation.Order;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageSourceInventoryConfiguration {

    @Value("${inventorydto.spring.messages.basename}")
    private String inventoryDtoSpringMessagesBasename;

    @Value("${inventorydto.spring.messages.encoding}")
    private String inventoryDtoSpringMessagesEncoding;

    @Bean
    public MessageSource inventoryDtoMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(inventoryDtoSpringMessagesBasename);
        messageSource.setDefaultEncoding(inventoryDtoSpringMessagesEncoding);
        return messageSource;
    }

    @Bean
    @Order(2)
    public LocalValidatorFactoryBean getValidatorInventoryDto() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(inventoryDtoMessageSource());
        return bean;
    }

}
