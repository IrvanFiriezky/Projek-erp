package id.cranium.erp.starter.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import id.cranium.erp.starter.dto.HttpHeaderDto;

@Service
@RequestScope
public class HttpHeaderService {
    
    private HttpHeaderDto httpHeaderDto;

    public HttpHeaderDto getHttpHeader() {
        return httpHeaderDto;
    }

    public void setHttpHeader(HttpHeaderDto httpHeaderDtoRequest) {
        if (httpHeaderDto == null) {
            httpHeaderDto = httpHeaderDtoRequest;
        }
    }
}
