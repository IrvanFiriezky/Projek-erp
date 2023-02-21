package id.cranium.erp.starter.client.user;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;
import java.time.Duration;
import java.util.List;
import id.cranium.erp.starter.dto.UserScopeMeDto;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.starter.exception.ServerException;

@Component
public class StarterUserWebClient {
    
    @Autowired
    private WebClient internalWebClient;

    @Value("${starter.user-service.url.scope.me}")
    private String userServiceUrlScopeMe;

    public UserScopeMeDto getMyScopeByName(String scopeName) throws ClientException, ServerException {
        String url = userServiceUrlScopeMe + "/" + scopeName;
        
        return internalWebClient.get()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(UserScopeMeDto.class)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                .filter(throwable -> throwable instanceof ServerException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                    throw new ServerException("External Service failed to process after max retries", HttpStatus.SERVICE_UNAVAILABLE.value());
                }))
            .publishOn(Schedulers.boundedElastic())
            .block();
	}

    public List<UserScopeMeDto> getMyScope() throws ClientException, ServerException {
        String url = userServiceUrlScopeMe;
        
        return internalWebClient.get()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<UserScopeMeDto>>() {})
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                .filter(throwable -> throwable instanceof ServerException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                    throw new ServerException("External Service failed to process after max retries", HttpStatus.SERVICE_UNAVAILABLE.value());
                }))
            .publishOn(Schedulers.boundedElastic())
            .block();
	}
}
