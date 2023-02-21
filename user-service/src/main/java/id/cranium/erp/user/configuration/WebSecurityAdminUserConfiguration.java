package id.cranium.erp.user.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import id.cranium.erp.user.security.UserJwtAuthenticationEntryPoint;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import id.cranium.erp.user.security.AdminUserJwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Order(1)
public class WebSecurityAdminUserConfiguration {

    private final AdminUserJwtAuthorizationFilter adminUserJwtAuthorizationFilter;
    private final UserJwtAuthenticationEntryPoint userJwtAuthenticationEntryPoint;

    @Value("${user.security.jwt.token.requestMatcherAdmin}")
    private String userTokenRequestMacherAdmin;

    @Bean
    public SecurityFilterChain adminUserFilterChain(HttpSecurity http) throws Exception {
        http
            .cors().disable()
            .csrf().disable()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling()
            .authenticationEntryPoint(userJwtAuthenticationEntryPoint)
            .and()
            .securityMatcher(userTokenRequestMacherAdmin + "/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(userTokenRequestMacherAdmin + "/**")
                .authenticated()
                .requestMatchers(userTokenRequestMacherAdmin + "/**")
                .hasRole("ERP_ADMIN")
                .and()
                .addFilterBefore(adminUserJwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            );
        
        return http.build();
    }

}
