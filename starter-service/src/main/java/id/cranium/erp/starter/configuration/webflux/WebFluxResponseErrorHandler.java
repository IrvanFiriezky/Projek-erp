package id.cranium.erp.starter.configuration.webflux;

import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.starter.exception.ServerException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebFluxResponseErrorHandler {
    
    public static Mono<ClientResponse> exchangeFilterResponseProcessor(ClientResponse response) {
        
        if (response.statusCode().is5xxServerError()) {
            return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new ServerException(body, response.statusCode().value())));
        }
        if (response.statusCode().is4xxClientError()) {
            return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new ClientException(body, response.statusCode().value())));
        }
        if (response.statusCode().isError()) {
            log.info("WebFluxResponseErrorHandler.exchangeFilterResponseProcessor: unhandled error code " + String.valueOf(response.statusCode().value()));
        }

        return Mono.just(response);
    }

    public static ExchangeFilterFunction getExchangeFilterFunction() {
        return ExchangeFilterFunction
            .ofResponseProcessor(WebFluxResponseErrorHandler::exchangeFilterResponseProcessor);
    }
}
