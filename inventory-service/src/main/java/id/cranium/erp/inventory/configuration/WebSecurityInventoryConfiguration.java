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
import id.cranium.erp.inventory.security.InventoryJwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Order(2)
public class WebSecurityInventoryConfiguration {

    private final InventoryJwtAuthorizationFilter inventoryJwtAuthorizationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Value("${inventory.security.jwt.token.requestMatcher}")
    private String inventoryTokenRequestMacher;

    @Bean
    public SecurityFilterChain inventoryFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .securityMatcher(inventoryTokenRequestMacher + "/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(inventoryTokenRequestMacher + "/**")
                        .authenticated()
                        .and()
                        .addFilterBefore(inventoryJwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class));

        return http.build();
    }

}
