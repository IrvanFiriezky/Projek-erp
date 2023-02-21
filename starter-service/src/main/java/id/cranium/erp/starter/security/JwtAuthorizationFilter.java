package id.cranium.erp.starter.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.apache.commons.lang3.RandomStringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import io.jsonwebtoken.JwtException;
import id.cranium.erp.starter.configuration.webflux.InternalWebFluxConfiguration;
import id.cranium.erp.starter.util.UserJwtUtil;
import id.cranium.erp.starter.exception.ServerException;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.starter.service.HttpHeaderService;
import id.cranium.erp.starter.dto.UserAuthInfoDto;
import id.cranium.erp.starter.dto.HttpHeaderDto;
import id.cranium.erp.starter.entity.JwtUserDetails;
import id.cranium.erp.starter.client.auth.StarterAuthWebClient;
import id.cranium.erp.starter.dto.LoginDto;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter {
 
    private final UserJwtUtil userJwtUtil;
    private final StarterAuthWebClient authWebClient;
    private final HttpHeaderService httpHeaderService;

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = null;

        if (authHeader == null || !authHeader.startsWith(InternalWebFluxConfiguration.TOKEN_PREFIX)) {
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
            isTokenValid = userJwtUtil.validateToken(jwtToken);
        } catch (JwtException ex) {
            log.info(ex.getMessage());
        }

        if (isTokenValid) {
            String permissions = userJwtUtil.extractAuthorities(jwtToken);
            List<GrantedAuthority> authorities = new ArrayList<>();

            if (!permissions.isEmpty()) {
                authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(permissions);
            }
    
            try {
                User user = new User(userJwtUtil.extractUsername(jwtToken), RandomStringUtils.randomAlphanumeric(60), authorities);
                LoginDto loginDto = authWebClient.getLoginByAccessToken(userJwtUtil.extractAccessToken(jwtToken));
                
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
            } catch (ServerException | ClientException ex) {
                log.info(ex.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
    
}
