package com.example.taskManager.configurations.security;

import com.example.taskManager.jwt.JwtTokenAuthFilter;
import com.example.taskManager.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenAuthFilter jwtTokenAuthFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtTokenAuthFilter jwtTokenAuthFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenAuthFilter = jwtTokenAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Отключить CSRF (для REST API не нужно)
                .authorizeHttpRequests(auth -> auth //authorizeHttpRequests задаёт правила доступа
                        .requestMatchers("/task-api/auth/**").permitAll()
                        .requestMatchers("/task-api/tasks/**").hasAnyRole("USER", "ADMIN") // USER или ADMIN
                        .anyRequest().authenticated()
                )
                //.httpBasic(withDefaults())  // Базовая авторизация
                //.formLogin(withDefaults())  // Стандартная форма авторизации
                .addFilterBefore(jwtTokenAuthFilter, UsernamePasswordAuthenticationFilter.class) // JWT-авторизация
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //UserDetailService для создания пользователей в RAM
    /*@Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))  // Пароль: "user" (захеширован)
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))  // Пароль: "admin" (захеширован)
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }*/

}
