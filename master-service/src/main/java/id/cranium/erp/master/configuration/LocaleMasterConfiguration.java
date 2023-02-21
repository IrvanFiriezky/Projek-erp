package id.cranium.erp.master.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class LocaleMasterConfiguration {
    
    @Value("${master.spring.messages.basename}")
    private String masterSpringMessagesBasename;

    @Value("${master.spring.messages.encoding}")
    private String masterSpringMessagesEncoding;

    @Bean
    public ResourceBundleMessageSource masterMessageSource() {
        final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames(masterSpringMessagesBasename);
        source.setDefaultEncoding(masterSpringMessagesEncoding);
        return source;
    }

}
