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
import id.cranium.erp.auth.security.AuthJwtAuthorizationFilter;
import id.cranium.erp.auth.security.JwtAuthenticationFailureHandler;
import id.cranium.erp.auth.service.JwtUserDetailsService;
import id.cranium.erp.auth.service.LoginService;
import id.cranium.erp.auth.util.UserAuthJwtUtil;
import id.cranium.erp.auth.security.UserDetailsAuthenticationProvider;
import id.cranium.erp.auth.security.UserJwtAuthenticationFilter;
import id.cranium.erp.starter.util.UserJwtUtil;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Order(2)
public class WebSecurityAuthConfiguration {
    
    private final AuthJwtAuthorizationFilter authJwtAuthorizationFilter;
    private final UserJwtUtil userJwtUtil;
    private final UserAuthJwtUtil userAuthJwtUtil;
    private final JwtUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthJwtAuthenticationEntryPoint authJwtAuthenticationEntryPoint;
    private final ResourceBundleMessageSource authMessageSource;
    private final LoginService loginService;

    @Value("${auth.security.jwt.token.filterUrl}")
    private String authTokenFilterUrl;

    @Value("${auth.security.jwt.token.requestMatcher}")
    private String authTokenRequestMacher;

    @Bean
    public AuthenticationManager userAuthenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(userProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain authFilterChain(HttpSecurity http, AuthenticationManager userAuthenticationManager) throws Exception {
        http 
            .cors().disable()
            .csrf().disable()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling()
            .authenticationEntryPoint(authJwtAuthenticationEntryPoint)
            .and()
            .addFilterBefore(userAuthenticationFilter(userAuthenticationManager), UsernamePasswordAuthenticationFilter.class)
            .securityMatcher(authTokenRequestMacher + "/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(authTokenFilterUrl)
                .permitAll()
                .requestMatchers(authTokenRequestMacher + "/**")
                .access(hasIpAddress("127.0.0.1"))
                .requestMatchers(authTokenRequestMacher + "/**")
                .authenticated()
                .and()
                .addFilterBefore(authJwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            );
        
        return http.build();
    }

    public UserJwtAuthenticationFilter userAuthenticationFilter(AuthenticationManager userAuthenticationManager) throws Exception {
        UserJwtAuthenticationFilter filter = new UserJwtAuthenticationFilter(userJwtUtil, userAuthJwtUtil, loginService);
        filter.setAuthenticationManager(userAuthenticationManager);
        filter.setFilterProcessesUrl(authTokenFilterUrl);
        filter.setAuthenticationFailureHandler(userAuthenticationFailureHandler());
        return filter;
    }

    public AuthenticationProvider userProvider() {
        UserDetailsAuthenticationProvider provider 
            = new UserDetailsAuthenticationProvider(passwordEncoder, userDetailsService);
        return provider;
    }

    public AuthenticationFailureHandler userAuthenticationFailureHandler() {
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
