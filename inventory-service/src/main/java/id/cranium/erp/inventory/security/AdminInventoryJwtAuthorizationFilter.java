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
import id.cranium.erp.starter.security.AdminJwtAuthorizationFilter;

@Component
@RequiredArgsConstructor
public class AdminInventoryJwtAuthorizationFilter extends OncePerRequestFilter {

    private final AdminJwtAuthorizationFilter adminJwtAuthorizationFilter;

    @Value("${inventory.security.jwt.token.requestMatcherAdmin}")
    private String inventoryTokenRequestMacherAdmin;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        adminJwtAuthorizationFilter.doFilterInternal(request, response, filterChain);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        boolean result = path.startsWith(inventoryTokenRequestMacherAdmin + "/");
        return !result;
    }
}
