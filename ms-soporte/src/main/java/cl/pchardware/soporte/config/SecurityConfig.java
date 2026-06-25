package cl.pchardware.soporte.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.pchardware.common.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

/**
 * Configuración de Spring Security para ms-soporte.
 *
 * Este microservicio actúa como SERVIDOR DE AUTENTICACIÓN:
 * - Expone /api/v1/auth/** como endpoints públicos (login y registro)
 * - Protege /api/v1/tickets-soporte/** según roles
 * - Usa sesiones STATELESS (sin cookies de sesión, solo JWT)
 *
 * Matriz de autorización:
 * ┌────────────────────────────┬────────┬────────────────────────────────────┐
 * │ Endpoint                   │ Método │ Acceso                             │
 * ├────────────────────────────┼────────┼────────────────────────────────────┤
 * │ /api/v1/auth/**            │ ALL    │ Público (sin token)                │
 * │ /actuator/**               │ ALL    │ Público (monitoreo)                │
 * │ /api/v1/tickets-soporte/** │ GET    │ ADMIN, TASADOR                     │
 * │ /api/v1/tickets-soporte/** │ POST   │ ADMIN                              │
 * │ /api/v1/tickets-soporte/** │ PUT    │ ADMIN                              │
 * │ /api/v1/tickets-soporte/** │ DELETE │ ADMIN                              │
 * │ Cualquier otro             │ ALL    │ Autenticado (con token válido)     │
 * └────────────────────────────┴────────┴────────────────────────────────────┘
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                // Actuator siempre público
                .requestMatchers("/actuator/**").permitAll()

                // Lectura de tickets: cualquier rol autenticado
                .requestMatchers(HttpMethod.GET, "/api/v1/tickets-soporte/**")
                    .hasAnyRole("Admin", "Tasador", "Cliente")

                // Escritura de tickets: solo Administrador y Tasador
                .requestMatchers(HttpMethod.POST, "/api/v1/tickets-soporte/**")
                    .hasAnyRole("Admin", "Tasador")
                .requestMatchers(HttpMethod.PUT, "/api/v1/tickets-soporte/**")
                    .hasAnyRole("Admin", "Tasador")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/tickets-soporte/**")
                    .hasAnyRole("Admin", "Tasador")

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}