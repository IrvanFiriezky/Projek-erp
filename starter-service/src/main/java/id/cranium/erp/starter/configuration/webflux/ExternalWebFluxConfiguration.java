package id.cranium.erp.starter.configuration.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ExternalWebFluxConfiguration implements WebFluxConfigurer {
    
    @Autowired
    private HttpClient webFluxHttpClient;

    @Bean
	public WebClient externalWebClient() {
		return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(webFluxHttpClient))
            .baseUrl("http://localhost:8080")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .filter(WebFluxResponseErrorHandler.getExchangeFilterFunction())
            .build();
	}
}
