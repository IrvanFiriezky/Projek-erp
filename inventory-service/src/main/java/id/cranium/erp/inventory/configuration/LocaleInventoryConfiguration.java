package id.cranium.erp.inventory.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class LocaleInventoryConfiguration {

    @Value("${inventory.spring.messages.basename}")
    private String inventorySpringMessagesBasename;

    @Value("${inventory.spring.messages.encoding}")
    private String inventorySpringMessagesEncoding;

    @Bean
    public ResourceBundleMessageSource inventoryMessageSource() {
        final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames(inventorySpringMessagesBasename);
        source.setDefaultEncoding(inventorySpringMessagesEncoding);
        return source;
    }

}
