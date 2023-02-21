package id.cranium.erp.master.configuration;

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
import id.cranium.erp.master.security.AdminMasterJwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Order(1)
public class WebSecurityAdminMasterConfiguration {

    private final AdminMasterJwtAuthorizationFilter adminMasterJwtAuthorizationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Value("${master.security.jwt.token.requestMatcherAdmin}")
    private String masterTokenRequestMacherAdmin;

    @Bean
    public SecurityFilterChain adminMasterFilterChain(HttpSecurity http) throws Exception {
        http
            .cors().disable()
            .csrf().disable()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .securityMatcher(masterTokenRequestMacherAdmin + "/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(masterTokenRequestMacherAdmin + "/**")
                .authenticated()
                .requestMatchers(masterTokenRequestMacherAdmin + "/**")
                .hasRole("ERP_ADMIN")
                .and()
                .addFilterBefore(adminMasterJwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            );
        
        return http.build();
    }

}
