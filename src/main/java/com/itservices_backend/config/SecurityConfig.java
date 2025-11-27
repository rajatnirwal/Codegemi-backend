package com.itservices_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsServiceImpl userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    // -----------------------------
    // MAIN SECURITY FILTER CHAIN
    // -----------------------------
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> {})   // enable CORS
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // Public APIs
                .requestMatchers("/", "/home", "/index", "/error").permitAll()
                .requestMatchers("/api/auth/**", "/api/contact/**").permitAll()

                // static files
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                // all others need authentication
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // -----------------------------
    // PASSWORD ENCODER
    // -----------------------------
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // -----------------------------
    // AUTH MANAGER
    // -----------------------------
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // -----------------------------
    // AUTH PROVIDER
    // -----------------------------
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    // -----------------------------
    // GLOBAL CORS CONFIG
    // -----------------------------
    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration cors = new CorsConfiguration();

        cors.setAllowCredentials(true);

        // ⭐ Allow your Hostinger React domain (replace with your actual domain)
        cors.addAllowedOriginPattern("https://*.codegemi.com");
        cors.addAllowedOriginPattern("http://*.codegemi.com");

        // ⭐ Local development allowed
        cors.addAllowedOrigin("http://localhost:5173");

        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);

        return new CorsFilter(source);
    }
}


// package com.itservices_backend.config;

// import lombok.RequiredArgsConstructor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// //--- new code ---
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;

// @Configuration
// public class SecurityConfig {

//     private final JwtAuthFilter jwtAuthFilter;
//     private final UserDetailsServiceImpl userDetailsService;

//     public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsServiceImpl userDetailsService) {
//         this.jwtAuthFilter = jwtAuthFilter;
//         this.userDetailsService = userDetailsService;
//     }
    
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http.csrf(csrf -> csrf.disable())
//                 .authorizeHttpRequests(auth -> auth
// 	            		 //.requestMatchers("/api/contact/**").permitAll()  // Allow all auth endpoints
//                          .requestMatchers("/api/auth/**").permitAll()

// 	                     .requestMatchers("/error").permitAll()        // Allow error endpoint
// 	                     .anyRequest().authenticated()
//                 )
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .authenticationProvider(authenticationProvider())
//                 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }

//     @Bean
//     public AuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//         authProvider.setUserDetailsService(userDetailsService);
//         authProvider.setPasswordEncoder(passwordEncoder());
//         return authProvider;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }
// //--- new code---
//     @Bean
// public CorsFilter corsFilter() {
//     CorsConfiguration corsConfiguration = new CorsConfiguration();
//     corsConfiguration.setAllowCredentials(true);
//     corsConfiguration.addAllowedOrigin("http://localhost:5173"); // React dev origin
//     corsConfiguration.addAllowedHeader("*");
//     corsConfiguration.addAllowedMethod("*");
    
//     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//     source.registerCorsConfiguration("/**", corsConfiguration);
//     return new CorsFilter(source);
// }
// }
