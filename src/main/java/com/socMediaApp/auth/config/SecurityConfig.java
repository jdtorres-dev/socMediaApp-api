package com.socMediaApp.auth.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.socMediaApp.filter.AuthEntryPoint;
import com.socMediaApp.filter.JwtAuthenticationFilter;
import com.socMediaApp.service.UserDetailsServiceImplementation;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImplementation userDetailsService;
    private final JwtAuthenticationFilter authenticationFilter;
    private final AuthEntryPoint exceptionHandler;

    public SecurityConfig(UserDetailsServiceImplementation userDetailsService, JwtAuthenticationFilter authenticationFilter, AuthEntryPoint exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
    }
     public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(
                new BCryptPasswordEncoder());
    }
     @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .cors(withDefaults())
                .sessionManagement(
                        (sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                    .requestMatchers("*").permitAll()
                    .anyRequest().authenticated())
                    .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(exceptionHandler));
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        
        // Allow credentials (cookies, authorization headers, etc.)
        corsConfiguration.setAllowCredentials(false); 
        
        // Allow only the frontend origin
        corsConfiguration.setAllowedOriginPatterns(List.of("http://localhost:3000"));
        
        // Allow all headers (you can restrict to Authorization if needed)
        corsConfiguration.addAllowedHeader("*");
        
        // Allow all HTTP methods (you can restrict to specific methods if needed)
        corsConfiguration.addAllowedMethod("*");
        
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}





