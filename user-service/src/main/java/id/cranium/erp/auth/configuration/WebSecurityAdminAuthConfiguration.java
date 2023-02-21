package id.cranium.erp.auth.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import id.cranium.erp.auth.security.AuthJwtAuthenticationEntryPoint;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import jakarta.servlet.http.HttpServletRequest;
import id.cranium.erp.auth.security.AdminAuthJwtAuthorizationFilter;
import id.cranium.erp.auth.security.JwtAuthenticationFailureHandler;
import id.cranium.erp.auth.service.JwtUserDetailsService;
import id.cranium.erp.auth.service.LoginService;
import id.cranium.erp.auth.util.AdminAuthJwtUtil;
import id.cranium.erp.auth.security.AdminUserDetailsAuthenticationProvider;
import id.cranium.erp.auth.security.AdminJwtAuthenticationFilter;
import id.cranium.erp.starter.util.AdminJwtUtil;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Order(1)
public class WebSecurityAdminAuthConfiguration {

    private final AdminAuthJwtAuthorizationFilter adminAuthJwtAuthorizationFilter;
    private final AdminJwtUtil adminJwtUtil;
    private final AdminAuthJwtUtil adminAuthJwtUtil;
    private final JwtUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthJwtAuthenticationEntryPoint authJwtAuthenticationEntryPoint;
    private final ResourceBundleMessageSource authMessageSource;
    private final LoginService loginService;

    @Value("${auth.security.jwt.token.filterUrlAdmin}")
    private String authTokenFilterUrlAdmin;

    @Value("${auth.security.jwt.token.requestMatcherAdmin}")
    private String authTokenRequestMacherAdmin;

    @Bean
    public AuthenticationManager adminAuthenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(adminProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain adminAuthFilterChain(HttpSecurity http, AuthenticationManager adminAuthenticationManager) throws Exception {
        http
            .cors().disable()
            .csrf().disable()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling()
            .authenticationEntryPoint(authJwtAuthenticationEntryPoint)
            .and()
            .addFilterBefore(adminAuthenticationFilter(adminAuthenticationManager), UsernamePasswordAuthenticationFilter.class)
            .securityMatcher(authTokenRequestMacherAdmin + "/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(authTokenFilterUrlAdmin)
                .permitAll()
                .requestMatchers(authTokenRequestMacherAdmin + "/**")
                .access(hasIpAddress("127.0.0.1"))
                .requestMatchers(authTokenRequestMacherAdmin + "/**")
                .authenticated()
                .requestMatchers(authTokenRequestMacherAdmin + "/**")
                .hasRole("ERP_ADMIN")
                .and()
                .addFilterBefore(adminAuthJwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            );
        
        return http.build();
    }

    public AdminJwtAuthenticationFilter adminAuthenticationFilter(AuthenticationManager adminAuthenticationManager) throws Exception {
        AdminJwtAuthenticationFilter filter = new AdminJwtAuthenticationFilter(adminJwtUtil, adminAuthJwtUtil, loginService);
        filter.setAuthenticationManager(adminAuthenticationManager);
        filter.setFilterProcessesUrl(authTokenFilterUrlAdmin);
        filter.setAuthenticationFailureHandler(adminAuthenticationFailureHandler());
        return filter;
    }

    public AuthenticationProvider adminProvider() {
        AdminUserDetailsAuthenticationProvider provider 
            = new AdminUserDetailsAuthenticationProvider(passwordEncoder, userDetailsService);
        return provider;
    }

    public AuthenticationFailureHandler adminAuthenticationFailureHandler() {
        return new JwtAuthenticationFailureHandler(authMessageSource, loginService);
    }

    private AuthorizationManager<RequestAuthorizationContext> hasIpAddress(String ipAddress) {
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(ipAddress);
        return (authentication, context) -> {
            HttpServletRequest request = context.getRequest();
            return new AuthorizationDecision(ipAddressMatcher.matches(request.getRemoteAddr()));
        };
    }

}
