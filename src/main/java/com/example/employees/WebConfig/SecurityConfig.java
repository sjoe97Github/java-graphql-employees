package com.example.employees.WebConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/employees/byFirstName").permitAll()
                        .requestMatchers("/api/schema/ddl").permitAll()
                )
                .httpBasic(withDefaults()); // or .formLogin() for form-based authentication

        return http.build();
    }
}