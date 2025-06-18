// src/main/java/com/snapthetitle/backend/config/SecurityConfig.java
package com.snapthetitle.backend.config;

import com.snapthetitle.backend.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ✅ 정적 리소스 허용
                        .requestMatchers("/uploads/**").permitAll()
                        // 로그인은 누구나
                        .requestMatchers(HttpMethod.POST, "/api/admin/login").permitAll()
                        // 관리자만 접근 가능
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // 그 외는 인증 필요
                        .anyRequest().authenticated()
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, AnonymousAuthenticationFilter.class);

        return http.build();
    }

}
