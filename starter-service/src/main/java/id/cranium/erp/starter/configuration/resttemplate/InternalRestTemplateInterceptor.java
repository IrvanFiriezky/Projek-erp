package id.cranium.erp.starter.configuration.resttemplate;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import id.cranium.erp.starter.service.HttpHeaderService;
import id.cranium.erp.starter.configuration.CorrelationConfiguration;
import id.cranium.erp.starter.configuration.LocaleStarterConfiguration;
import java.io.IOException;
import java.util.Objects;
import org.slf4j.MDC;

public class InternalRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_USER_ID = "userId";
    private static final String HEADER_API_VERSION = "X-Api-Version";

    private HttpHeaderService httpHeaderService;

    public InternalRestTemplateInterceptor(HttpHeaderService httpHeaderService) {
        this.httpHeaderService = httpHeaderService;
    }
    
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if(!Objects.isNull(httpHeaderService.getHttpHeader())){
            request.getHeaders().add(HEADER_USER_ID, Long.toString(httpHeaderService.getHttpHeader().getXUserId()));
            request.getHeaders().add(HEADER_API_VERSION, httpHeaderService.getHttpHeader().getXApiVersion());
            request.getHeaders().add(HttpHeaders.ACCEPT_LANGUAGE, httpHeaderService.getHttpHeader().getXAcceptLanguage());
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + httpHeaderService.getHttpHeader().getXAuthorization());
        } else {
            request.getHeaders().add(HEADER_USER_ID, "0");
            request.getHeaders().add(HEADER_API_VERSION, "1");
            request.getHeaders().add(HttpHeaders.ACCEPT_LANGUAGE, LocaleStarterConfiguration.DEFAULT_LOCALE.toLanguageTag());
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + "");
        }
        
        request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        request.getHeaders().add(CorrelationConfiguration.REQUEST_ID_HEADER_NAME, MDC.get(CorrelationConfiguration.REQUEST_ID_HEADER_NAME));

        ClientHttpResponse response = execution.execute(request, body);

        return response;
    }

}
