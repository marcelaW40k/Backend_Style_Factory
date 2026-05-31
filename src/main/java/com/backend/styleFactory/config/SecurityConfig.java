package com.backend.styleFactory.config;

import com.backend.styleFactory.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad de la aplicación.
 * Define las reglas de acceso a las rutas y la integración del filtro JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtFilter jwtFilter,
                          AuthenticationProvider authenticationProvider) {
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Cadena de filtros de seguridad que establece las políticas de sesión,
     * protección CSRF, autorización de rutas y agrega el filtro JWT.
     *
     * @param http Objeto HttpSecurity proporcionado por Spring
     * @return SecurityFilterChain configurado
     * @throws Exception si ocurre un error en la configuración
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(http))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        // Endpoints restringidos por rol
                        .requestMatchers(HttpMethod.GET, "/servicios/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/servicios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/servicios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/servicios/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/reservas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/reservas/**").hasAnyRole("ADMIN", "CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/reservas/**").hasAnyRole("ADMIN", "CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/reservas/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/empleados/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/empleados/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/empleados/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/empleados/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/horarios/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/horarios/**").hasAnyRole("ADMIN", "CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/horarios/**").hasAnyRole("ADMIN", "CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/horarios/**").hasAnyRole("ADMIN", "CLIENTE")
                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                // Registrar el AuthenticationProvider que usa la BD.
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}