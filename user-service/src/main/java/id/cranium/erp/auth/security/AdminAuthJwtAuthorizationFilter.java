package id.cranium.erp.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;
import org.apache.commons.lang3.RandomStringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import io.jsonwebtoken.JwtException;
import id.cranium.erp.starter.configuration.webflux.InternalWebFluxConfiguration;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.service.HttpHeaderService;
import id.cranium.erp.starter.dto.UserAuthInfoDto;
import id.cranium.erp.starter.dto.HttpHeaderDto;
import id.cranium.erp.starter.entity.JwtUserDetails;
import id.cranium.erp.starter.security.JwtAuthenticationToken;
import id.cranium.erp.starter.util.AdminJwtUtil;
import id.cranium.erp.auth.service.LoginService;
import id.cranium.erp.user.dto.LoginDto;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminAuthJwtAuthorizationFilter extends OncePerRequestFilter {
 
    private final AdminJwtUtil adminJwtUtil;
    private final LoginService loginService;
    private final HttpHeaderService httpHeaderService;

    private String TOKEN_PREFIX = "Bearer ";

    @Value("${auth.security.jwt.token.requestMatcherAdmin}")
    private String authTokenRequestMacherAdmin;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = null;

        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
/*
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("token")) {
                jwtToken = cookie.getValue();
            }
        }
*/
        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean isTokenValid = false;
        try {
            isTokenValid = adminJwtUtil.validateToken(jwtToken);
        } catch (JwtException ex) {
            log.info(ex.getMessage());
        }

        if (isTokenValid) {
            String permissions = adminJwtUtil.extractAuthorities(jwtToken);

            List<GrantedAuthority> authorities = new ArrayList<>();
            if (!permissions.isEmpty()) {
                authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(permissions);
            }

            try {
                User user = new User(adminJwtUtil.extractUsername(jwtToken), RandomStringUtils.randomAlphanumeric(60), authorities);
                LoginDto loginDto = loginService.findByAccessTokenStatusDeleted(adminJwtUtil.extractAccessToken(jwtToken));

                HttpHeaderDto httpHeaderDto = HttpHeaderDto.builder()
                    .XUserId(Long.toString(loginDto.getUserId()))
                    .XApiVersion(request.getHeader(InternalWebFluxConfiguration.HEADER_API_VERSION))
                    .XAcceptLanguage(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE))
                    .XAuthorization(jwtToken)
                    .build();
                httpHeaderService.setHttpHeader(httpHeaderDto);
                
                UserAuthInfoDto userAuthInfoDto = UserAuthInfoDto.builder()
                    .userId(loginDto.getUserId())
                    .username(loginDto.getUsername())
                    .build();
                JwtUserDetails jwtUserDetails = new JwtUserDetails(user, userAuthInfoDto);
            
                JwtAuthenticationToken authToken = new JwtAuthenticationToken(jwtUserDetails,
                        null, authorities, null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (DataNotFoundException ex) {
                log.info(ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        boolean result = path.startsWith(authTokenRequestMacherAdmin + "/");
        return !result;
    }
    
}
