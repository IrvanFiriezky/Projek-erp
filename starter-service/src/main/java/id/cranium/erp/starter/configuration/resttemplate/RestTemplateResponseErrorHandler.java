package id.cranium.erp.starter.configuration.resttemplate;

import java.io.IOException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.starter.exception.ServerException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        String body = getBody(httpResponse);

        if (httpResponse.getStatusCode().is5xxServerError()) {
            //Handle SERVER_ERROR
            throw new ServerException(body, httpResponse.getStatusCode().value());
        } else if (httpResponse.getStatusCode().is4xxClientError()) {
            //Handle CLIENT_ERROR
            throw new ClientException(body, httpResponse.getStatusCode().value());
        }

        log.info("RestTemplateResponseErrorHandler.handleError: unhandled error code " + httpResponse.getStatusCode().value() + " " + body);
    }

    private String getBody(ClientHttpResponse httpResponse) {
        String body = "";

        try {
            body = httpResponse.getStatusText();
            body = new ObjectMapper().readValue(httpResponse.getBody(), String.class);
            return body;
        } catch (IOException ex) {
            return body;
        }
    }
}
