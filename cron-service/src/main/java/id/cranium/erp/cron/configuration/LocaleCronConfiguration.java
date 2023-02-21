package id.cranium.erp.cron.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import java.util.Locale;

@Configuration
public class LocaleCronConfiguration {
    
    @Value("${cron.spring.messages.basename}")
    private String cronSpringMessagesBasename;

    @Value("${cron.spring.messages.encoding}")
    private String cronSpringMessagesEncoding;
    
    @Bean
    public ResourceBundleMessageSource cronMessageSource() {
        final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames(cronSpringMessagesBasename);
        source.setDefaultEncoding(cronSpringMessagesEncoding);
        return source;
    }

}
