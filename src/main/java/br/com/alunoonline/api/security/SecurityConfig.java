package br.com.alunoonline.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    // FILTRO DE PERMISSÃO DOS ENDPOINTS
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // ACESSO LIVRE
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();

                    // === SOMENTE ADMIN ===
                    req.requestMatchers("/alunoscasa/**").hasRole("ADMIN");
                    req.requestMatchers("/professorescasa/**").hasRole("ADMIN");
                    req.requestMatchers("/disciplinascasa/**").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.POST, "/matriculascasa").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.PATCH, "/matriculascasa/trancar/**").hasRole("ADMIN");

                    // === PROFESSOR (e admin) ===
                    req.requestMatchers(HttpMethod.PATCH, "/matriculascasa/atualizar-notas/**").hasAnyRole("PROFESSOR", "ADMIN");

                    // === ALUNOS (e admin) ===
                    req.requestMatchers(HttpMethod.GET, "/matriculascasa/emitir-historico/**").authenticated();


                    // Outras requisições
                    req.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}