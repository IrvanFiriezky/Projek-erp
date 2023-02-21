package id.cranium.erp.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class LocaleAuthConfiguration {

    @Value("${auth.spring.messages.basename}")
    private String authSpringMessagesBasename;

    @Value("${auth.spring.messages.encoding}")
    private String authSpringMessagesEncoding;

    @Bean
    public ResourceBundleMessageSource authMessageSource() {
        final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames(authSpringMessagesBasename);
        source.setDefaultEncoding(authSpringMessagesEncoding);
        return source;
    }
    
}
