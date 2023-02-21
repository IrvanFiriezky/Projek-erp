package id.cranium.erp.starter.configuration.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import id.cranium.erp.starter.service.HttpHeaderService;
import java.util.List;
import java.util.ArrayList;

@Configuration
public class InternalRestTemplateConfiguration {

    @Autowired
    private HttpHeaderService httpHeaderService;

    @Autowired
    private HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory;

    @Bean
    public RestTemplate internalRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new InternalRestTemplateInterceptor(httpHeaderService));
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }

}
