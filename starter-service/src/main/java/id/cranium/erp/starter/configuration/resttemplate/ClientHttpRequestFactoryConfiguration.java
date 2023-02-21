package id.cranium.erp.starter.configuration.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.DefaultConnectionKeepAliveStrategy;
import org.apache.hc.core5.util.Timeout;
import org.apache.hc.core5.http.io.SocketConfig;

@Configuration
public class ClientHttpRequestFactoryConfiguration {

    @Value("${starter.http.outgoing.readTimeout}")
    private int readTimeout;

    @Value("${starter.http.outgoing.connectionTimeout}")
    private int connectionTimeout;

    @Value("${starter.http.outgoing.maxPooling}")
    private int maxPooling;

    @Bean
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(Timeout.ofMilliseconds(readTimeout)).build();

        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(maxPooling);
        poolingConnectionManager.setDefaultMaxPerRoute(maxPooling);
        poolingConnectionManager.setDefaultSocketConfig(socketConfig);

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(poolingConnectionManager);
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        CloseableHttpClient httpClient = httpClientBuilder.build();
        
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(httpClient);
        clientHttpRequestFactory.setConnectTimeout(connectionTimeout);

        return clientHttpRequestFactory;
    }
}
