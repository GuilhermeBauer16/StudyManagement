//package com.github.guilhermebauer.studymanagement.config;
//
//import com.github.guilhermebauer.studymanagement.filters.CsrfCookieFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//
//public class SecurityConfig {
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
//        requestAttributeHandler.setCsrfRequestAttributeName("_csrf");
//
//        return http
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .csrf(csrf -> csrf.csrfTokenRequestHandler(requestAttributeHandler)
//                        .ignoringRequestMatchers("*")
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                )
//                .cors(cors -> corsConfigurationSource())
//                .addFilterBefore(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//                .sessionManagement(
//                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(
//                        authorizeHttpRequests -> authorizeHttpRequests
//                                .requestMatchers("/api/role/**").permitAll()
////                                .requestMatchers("/api/workoutExercise/**").hasAuthority("ADMIN")
////                                .requestMatchers("/api/personalizedWorkout/**").hasAnyAuthority("USER", "ADMIN")
////                                .requestMatchers("/api/**").authenticated()
////                                .requestMatchers("/users").denyAll()
//                )
//                .build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("*"));
//        configuration.setAllowedMethods(List.of("*"));
//        configuration.setAllowedHeaders(List.of("*"));
//
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
//                new UrlBasedCorsConfigurationSource();
//
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
//        return urlBasedCorsConfigurationSource;
//    }
//}
