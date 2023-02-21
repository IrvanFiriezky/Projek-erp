package id.cranium.erp.inventory.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import id.cranium.erp.starter.security.JwtAuthorizationFilter;

@Component
@RequiredArgsConstructor
public class InventoryJwtAuthorizationFilter extends OncePerRequestFilter {
 
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Value("${inventory.security.jwt.token.requestMatcher}")
    private String inventoryTokenRequestMacher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        boolean result = path.startsWith(inventoryTokenRequestMacher + "/");
        return !result;
    }
}
