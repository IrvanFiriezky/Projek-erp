package id.cranium.erp.user.client.master;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import reactor.core.publisher.Mono;
//import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;
import java.time.Duration;
import id.cranium.erp.master.dto.ProductDto;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.starter.exception.ServerException;

@Component
public class UserProductWebClient {
    
    @Autowired
    private WebClient internalWebClient;

    @Value("${user.master-service.url.product}")
    private String masterServiceUrlProduct;

    public ProductDto getProductById(Long id) throws ClientException, ServerException {
        String url = masterServiceUrlProduct + "/" + id;
        
        return internalWebClient.get()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ProductDto.class)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                .filter(throwable -> throwable instanceof ServerException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                    throw new ServerException("External Service failed to process after max retries", HttpStatus.SERVICE_UNAVAILABLE.value());
                }))
            .publishOn(Schedulers.boundedElastic())
            .block();
	}
}
