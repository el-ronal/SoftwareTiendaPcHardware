package cl.pchardware.catalogo.config;

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
 * Configuración de Spring Security para ms-catalogo.
 *
 * Este microservicio actúa como RESOURCE SERVER:
 * - NO emite tokens JWT (eso lo hace ms-usuarios)
 * - Valida tokens JWT en cada petición usando el filtro compartido
 * - Aplica reglas de autorización según el rol del usuario
 *
 * Matriz de autorización:
 * ┌────────────────────────────┬────────┬────────────────────────────────────┐
 * │ Endpoint                   │ Método │ Acceso                             │
 * ├────────────────────────────┼────────┼────────────────────────────────────┤
 * │ /api/v1/auth/**            │ ALL    │ Público (sin token)                │
 * │ /actuator/**               │ ALL    │ Público (monitoreo)                │
 * │ /api/v1/categorias/**      │ GET    │ ADMIN, TASADOR                     │
 * │ /api/v1/categorias/**      │ POST   │ ADMIN                              │
 * │ /api/v1/categorias/**      │ PUT    │ ADMIN                              │
 * │ /api/v1/categorias/**      │ DELETE │ ADMIN                              │
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

                // Lectura de categorías: cualquier rol autenticado
                .requestMatchers(HttpMethod.GET, "/api/v1/categorias/**")
                    .hasAnyRole("Admin", "Tasador", "Cliente")

                // Escritura de categorías: solo Administrador y Tasador
                .requestMatchers(HttpMethod.POST, "/api/v1/categorias/**")
                    .hasAnyRole("Admin", "Tasador")
                .requestMatchers(HttpMethod.PUT, "/api/v1/categorias/**")
                    .hasAnyRole("Admin", "Tasador")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/categorias/**")
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