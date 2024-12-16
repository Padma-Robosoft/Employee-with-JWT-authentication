package com.example.Employee_Details.config;

import com.example.Employee_Details.jwt.AuthEntryPointJwt;
import com.example.Employee_Details.jwt.AuthTokenFilter;
import com.example.Employee_Details.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(authorizeRequests ->
                    authorizeRequests
                            .requestMatchers("/api/v1/register").permitAll()
                            .requestMatchers("/api/v1/login").permitAll()
                            .requestMatchers("/swagger-ui.html").permitAll()
                            .requestMatchers("/swagger-ui/index.html").permitAll()
                            .requestMatchers("/api/admin").hasRole("ADMIN")
                            .requestMatchers("/api/user").hasRole("USER")
                            .requestMatchers("/api/v1/delete-employee").hasRole("ADMIN")
                            .requestMatchers("/error", "/error/**").permitAll()
                            .requestMatchers("/actuator/health").permitAll()
                            .requestMatchers("/actuator/info").permitAll()
                            .requestMatchers("/actuator/metrics").permitAll()

                            .anyRequest().authenticated()
            );

            http.sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(unauthorizedHandler)
        );

            http.csrf(AbstractHttpConfigurer::disable);
            http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

            return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .map(user -> {
                    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

                    return new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getPassword(),
                            Collections.singleton(authority));
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
