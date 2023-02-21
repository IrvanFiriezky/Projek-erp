package id.cranium.erp.starter.configuration.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ClientRequest;
import reactor.netty.http.client.HttpClient;
import java.util.Objects;
import org.slf4j.MDC;
import id.cranium.erp.starter.service.HttpHeaderService;
import id.cranium.erp.starter.configuration.CorrelationConfiguration;
import id.cranium.erp.starter.configuration.LocaleStarterConfiguration;

@Configuration
public class InternalWebFluxConfiguration implements WebFluxConfigurer {

    @Autowired
    private HttpHeaderService httpHeaderService;

    @Autowired
    private HttpClient webFluxHttpClient;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_USER_ID = "userId";
    public static final String HEADER_API_VERSION = "X-Api-Version";
    
    @Bean
	public WebClient internalWebClient() {
		return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(webFluxHttpClient))
            .baseUrl("http://localhost:8080")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .filter(WebFluxResponseErrorHandler.getExchangeFilterFunction())
            .filter((request, next) -> {
                String userId = "0";
                String apiVersion = "1";
                String acceptLanguage = LocaleStarterConfiguration.DEFAULT_LOCALE.toLanguageTag();
                String authorization = TOKEN_PREFIX + "";

                if(!Objects.isNull(httpHeaderService.getHttpHeader())){
                    userId = Long.toString(httpHeaderService.getHttpHeader().getXUserId());
                    apiVersion = httpHeaderService.getHttpHeader().getXApiVersion();
                    acceptLanguage = httpHeaderService.getHttpHeader().getXAcceptLanguage();
                    authorization = TOKEN_PREFIX + httpHeaderService.getHttpHeader().getXAuthorization();
                }

                ClientRequest filtered = ClientRequest.from(request)
                    .header(HEADER_USER_ID, userId)
                    .header(HEADER_API_VERSION, apiVersion)
                    .header(HttpHeaders.ACCEPT_LANGUAGE, acceptLanguage)
                    .header(HttpHeaders.AUTHORIZATION, authorization)
                    .header(CorrelationConfiguration.REQUEST_ID_HEADER_NAME, MDC.get(CorrelationConfiguration.REQUEST_ID_HEADER_NAME))
                    .build();

                return next.exchange(filtered);
            })
            .build();
	}
}
