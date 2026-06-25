package cl.pchardware.usuarios.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.pchardware.common.security.JwtProperties;
import cl.pchardware.common.security.JwtTokenProvider;
import cl.pchardware.usuarios.dto.LoginRequest;
import cl.pchardware.usuarios.dto.LoginResponse;
import cl.pchardware.usuarios.dto.RegisterRequest;
import cl.pchardware.usuarios.dto.UsuarioResponse;
import cl.pchardware.usuarios.mapper.UsuarioMapper;
import cl.pchardware.usuarios.model.Perfil;
import cl.pchardware.usuarios.model.Rol;
import cl.pchardware.usuarios.model.Usuario;
import cl.pchardware.usuarios.repository.PerfilRepository;
import cl.pchardware.usuarios.repository.RolRepository;
import cl.pchardware.usuarios.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Servicio de autenticación y registro de usuarios.
 *
 * Responsabilidades:
 * - Autenticar usuarios validando email/password contra la BD
 * - Generar tokens JWT tras autenticación exitosa
 * - Registrar nuevos usuarios con contraseña hasheada (BCrypt)
 * - Gestionar bloqueo de cuentas por intentos fallidos
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository; // Necesario para tu tabla 'perfil'
    private final RolRepository rolRepository;       // Necesario para asignar el id_rol
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    /**
     * Autentica un usuario y genera un token JWT.
     * Adaptado a la BD de la tienda usando la columna 'estado'.
     */
    @Transactional
    public LoginResponse login(LoginRequest request) {

        // 1. Buscar usuario
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Intento de login con email inexistente: {}", request.getEmail());
                    return new RuntimeException("Credenciales inválidas");
                });

        // 2. Verificar estado del usuario (ACTIVO, INACTIVO, BANEADO)
        if ("INACTIVO".equalsIgnoreCase(usuario.getEstado())) {
            log.warn("Intento de login con cuenta inactiva: {}", request.getEmail());
            throw new RuntimeException("La cuenta está desactivada. Contacte al administrador.");
        }

        if ("BANEADO".equalsIgnoreCase(usuario.getEstado())) {
            log.warn("Intento de login con cuenta baneada: {}", request.getEmail());
            throw new RuntimeException("La cuenta ha sido baneada. Contacte al administrador.");
        }

        // 3. Validar contraseña contra el password_hash de tu BD
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            log.warn("Contraseña incorrecta para: {}", request.getEmail());
            throw new RuntimeException("Credenciales inválidas");
        }

        // 4. Obtener nombre completo desde el perfil y nombre de rol (Adaptado a tu BD)
        String nombreCompleto = usuario.getPerfil() != null ? usuario.getPerfil().getNombreCompleto() : "Usuario Sin Perfil";
        String nombreRol = usuario.getRol() != null ? usuario.getRol().getNombre() : "CLIENTE";

        // 5. Generar token JWT
        String token = jwtTokenProvider.generarToken(
            usuario.getEmail(),
            nombreRol,
            nombreCompleto
        );

        log.info("Login exitoso para: {} con rol: {}", usuario.getEmail(), nombreRol);

        return LoginResponse.builder()
                .token(token)
                .email(usuario.getEmail())
                .nombre(nombreCompleto)
                .rol(nombreRol)
                .expiresIn(jwtProperties.getExpirationMs())
                .build();
    }

    /**
     * Registra un nuevo usuario con rol "CLIENTE" y contraseña hasheada.
     * Adaptado para insertar en las tablas 'usuario' y 'perfil'.
     */
    @Transactional
    public UsuarioResponse register(RegisterRequest request) {

        // 1. Verificar email único
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + request.getEmail());
        }

        // 2. Buscar el rol por defecto (ID 2 = CLIENTE según tus scripts)
        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado en la base de datos"));

        // 3. Crear usuario (Reemplaza la lógica de credenciales por el estado 'ACTIVO')
        Usuario usuario = Usuario.builder()
                .rol(rolCliente)
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .estado("ACTIVO") // Se asigna estado ACTIVO según tu BD
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 4. Crear el perfil asociado (Requisito de tu base de datos)
        // Nota: Asumo que tu RegisterRequest ahora incluye rut, nombreCompleto y telefono
        Perfil perfil = Perfil.builder()
                .usuario(usuarioGuardado)
                .rut(request.getRut())
                .nombreCompleto(request.getNombreCompleto())
                .telefono(request.getTelefono())
                .build();

        perfilRepository.save(perfil);

        log.info("Usuario y perfil registrados exitosamente: {}", usuarioGuardado.getEmail());

        return usuarioMapper.toResponse(usuarioGuardado);
    }
}
