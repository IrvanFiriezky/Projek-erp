package id.cranium.erp.inventory.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import id.cranium.erp.starter.security.JwtAuthenticationEntryPoint;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import id.cranium.erp.inventory.security.AdminInventoryJwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Order(1)
public class WebSecurityAdminInventoryConfiguration {

    private final AdminInventoryJwtAuthorizationFilter adminInventoryJwtAuthorizationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Value("${inventory.security.jwt.token.requestMatcherAdmin}")
    private String inventoryTokenRequestMacherAdmin;

    @Bean
    public SecurityFilterChain adminInventoryFilterChain(HttpSecurity http) throws Exception {
        http
            .cors().disable()
            .csrf().disable()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .securityMatcher(inventoryTokenRequestMacherAdmin + "/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(inventoryTokenRequestMacherAdmin + "/**")
                .authenticated()
                .requestMatchers(inventoryTokenRequestMacherAdmin + "/**")
                .hasRole("ERP_ADMIN")
                .and()
                .addFilterBefore(adminInventoryJwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            );
        
        return http.build();
    }

}
