package id.cranium.erp.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class LocaleUserConfiguration {
    
    @Value("${user.spring.messages.basename}")
    private String userSpringMessagesBasename;

    @Value("${user.spring.messages.encoding}")
    private String userSpringMessagesEncoding;

    @Bean
    public ResourceBundleMessageSource userMessageSource() {
        final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames(userSpringMessagesBasename);
        source.setDefaultEncoding(userSpringMessagesEncoding);
        return source;
    }
}
