package id.cranium.erp.starter.configuration.resttemplate;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.MediaType;
import id.cranium.erp.starter.service.HttpHeaderService;
import id.cranium.erp.starter.configuration.CorrelationConfiguration;
import java.io.IOException;
import org.slf4j.MDC;

public class ExternalRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private HttpHeaderService httpHeaderService;

    public ExternalRestTemplateInterceptor(HttpHeaderService httpHeaderService) {
        this.httpHeaderService = httpHeaderService;
    }
    
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        
        request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        request.getHeaders().add(CorrelationConfiguration.REQUEST_ID_HEADER_NAME, MDC.get(CorrelationConfiguration.REQUEST_ID_HEADER_NAME));

        ClientHttpResponse response = execution.execute(request, body);

        return response;
    }
    
}
