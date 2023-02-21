package id.cranium.erp.inventory.client.user;

public class InventoryUserWebClient {

}
/*
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;
import java.time.Duration;
import id.cranium.erp.user.dto.UserDto;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.starter.exception.ServerException;

@Component
public class InventoryUserWebClient {
    
    @Autowired
    private WebClient internalWebClient;

    @Value("${inventory.user-service.url.user}")
    private String userServiceUrlUser;

    public UserDto getUserById(Long id) throws ClientException, ServerException {
        String url = userServiceUrlUser + "/" + id;
        
        return internalWebClient.get()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(UserDto.class)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                .filter(throwable -> throwable instanceof ServerException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                    throw new ServerException("External Service failed to process after max retries", HttpStatus.SERVICE_UNAVAILABLE.value());
                }))
            .publishOn(Schedulers.boundedElastic())
            .block();
	}
    
}
*/