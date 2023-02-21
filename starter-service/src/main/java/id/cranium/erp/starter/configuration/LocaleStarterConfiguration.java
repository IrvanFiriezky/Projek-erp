package id.cranium.erp.starter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import java.util.Locale;

@Configuration
public class LocaleStarterConfiguration {
    
    @Value("${starter.spring.messages.basename}")
    private String starterSpringMessagesBasename;

    @Value("${starter.spring.messages.encoding}")
    private String starterSpringMessagesEncoding;

    public final static Locale DEFAULT_LOCALE = Locale.forLanguageTag("id-ID");
    
    @Bean
    public AcceptHeaderLocaleResolver starterLocaleResolver() {
        final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(DEFAULT_LOCALE);
        Locale.setDefault(DEFAULT_LOCALE);
        return resolver;
    }

    @Bean
    public ResourceBundleMessageSource starterMessageSource() {
        final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames(starterSpringMessagesBasename);
        source.setDefaultEncoding(starterSpringMessagesEncoding);
        return source;
    }

}
