package id.cranium.erp.starter.client.auth;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;
import java.time.Duration;
import id.cranium.erp.starter.dto.LoginDto;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.starter.exception.ServerException;

@Component
public class StarterAuthWebClient {
    
    @Autowired
    private WebClient internalWebClient;

    @Value("${starter.auth-service.url.verification.token}")
    private String authServiceUrlLogin;

    public LoginDto getLoginByAccessToken(String accessToken) throws ClientException, ServerException {
        String url = authServiceUrlLogin + "/" + accessToken;
        
        return internalWebClient.get()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(LoginDto.class)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                .filter(throwable -> throwable instanceof ServerException)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                    throw new ServerException("External Service failed to process after max retries", HttpStatus.SERVICE_UNAVAILABLE.value());
                }))
            .publishOn(Schedulers.boundedElastic())
            .block();
	}
}
