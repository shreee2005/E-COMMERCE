package com.E_Commerce.E_Commerce.Config;


import com.E_Commerce.E_Commerce.Security.JwtAuthenticationEntrypoint;
import com.E_Commerce.E_Commerce.Security.JwtAuthenticationEntrypoint;
import com.E_Commerce.E_Commerce.Security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntrypoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter; // <-- The filter to be injected

    // Inject dependencies via constructor
    public SpringSecurityConfig(UserDetailsService userDetailsService,
                                JwtAuthenticationEntrypoint authenticationEntryPoint,
                                JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // In: com/E_Commerce/E_Commerce/Config/SpringSecurityConfig.java
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Tell the provider how to find users
        authProvider.setUserDetailsService(userDetailsService);

        // Tell the provider how to check passwords
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize
                                // Allow public access for registration and login
                                .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                                // IMPORTANT: Allow anyone to VIEW products
                                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                                .requestMatchers(HttpMethod.POST , "/api/cart/**").permitAll()
                                .requestMatchers(HttpMethod.POST , "/api/orders/**").permitAll()
                                // All other requests must be authenticated
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(authenticationEntryPoint)
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}