package id.cranium.erp.starter.configuration;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.util.ObjectUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationConfiguration extends OncePerRequestFilter {

    public static final String REQUEST_ID_HEADER_NAME = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestId = extractOrGenerateRequestId(request);
        MDC.put(REQUEST_ID_HEADER_NAME, requestId);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(request, responseWrapper);

        responseWrapper.setHeader(REQUEST_ID_HEADER_NAME, requestId);
        responseWrapper.copyBodyToResponse();
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }

    private String extractOrGenerateRequestId(HttpServletRequest request) {
        String headerRequestId = request.getHeader(REQUEST_ID_HEADER_NAME);

        return ObjectUtils.isEmpty(headerRequestId)
                ? generateUUID()
                : headerRequestId;
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateAndSetMDCRequestId() {
        String requestId = generateUUID();
        MDC.put(REQUEST_ID_HEADER_NAME, requestId);
        return requestId;
    }
}
