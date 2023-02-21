package id.cranium.erp.starter.configuration.webflux;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.http.client.HttpClient;
import java.util.concurrent.TimeUnit;
import java.time.Duration;

@Component
public class HttpClientWebFlux {
    
    @Value("${starter.http.outgoing.readTimeout}")
    private int readTimeout;

    @Value("${starter.http.outgoing.writeTimeout}")
    private int writeTimeout;

    @Value("${starter.http.outgoing.connectionTimeout}")
    private int connectionTimeout;

    @Value("${starter.http.outgoing.maxPooling}")
    private int maxPooling;

    @Bean
    public HttpClient webFluxHttpClient() {
        return HttpClient.create(getConnectionProvider())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout)
                .doOnConnected(conn -> conn
                    .addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS)));
    }

    private ConnectionProvider getConnectionProvider() {
        return ConnectionProvider.builder("custom")
            .maxConnections(maxPooling)
            .maxIdleTime(Duration.ofSeconds(20))           
            .maxLifeTime(Duration.ofSeconds(60))           
            .pendingAcquireTimeout(Duration.ofSeconds(60)) 
            .evictInBackground(Duration.ofSeconds(120))    
            .build();
    }
}
