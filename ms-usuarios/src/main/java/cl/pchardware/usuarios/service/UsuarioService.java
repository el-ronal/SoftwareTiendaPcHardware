package cl.pchardware.usuarios.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cl.pchardware.usuarios.dto.UsuarioRequest;
import cl.pchardware.usuarios.dto.UsuarioResponse;
import cl.pchardware.usuarios.mapper.UsuarioMapper;
import cl.pchardware.usuarios.model.Perfil;
import cl.pchardware.usuarios.model.Usuario;
import cl.pchardware.usuarios.repository.UsuarioRepository;
import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    // Inyectamos los servicios hermanos
    private final RolService rolService;
    private final PerfilService perfilService;

    public List<UsuarioResponse> findAll() {
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    public UsuarioResponse findById(Long id) {
        return usuarioMapper.toResponse(getUsuarioById(id));
    }
    
    @Transactional(readOnly = true)
    public List<UsuarioResponse> findByEstado(String estado) {
        // Opcional: Podrías validar que el estado sea ACTIVO, INACTIVO o BANEADO aquí
        List<Usuario> usuarios = usuarioRepository.findByEstado(estado.toUpperCase());
        return usuarioMapper.toResponseList(usuarios);
    }

    @Transactional
    public UsuarioResponse create(UsuarioRequest request) {
        validateEmailUnico(request.getEmail());

        Usuario usuario = usuarioMapper.toEntity(request);
        
        // 1. Delegamos la obtención del Rol
        usuario.setRol(rolService.getRolByNombre(request.getRol()));
        
        // 2. Asignamos la contraseña directamente (como en el ejemplo del profesor)
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        usuario.setPasswordHash(hashedPassword);

        // 3. Delegamos la construcción del Perfil
        Perfil perfil = perfilService.buildPerfilParaUsuario(request.getPerfil(), usuario);
        usuario.setPerfil(perfil);

        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponse update(Long id, UsuarioRequest request) {
        Usuario usuario = getUsuarioById(id);

        if (!usuario.getEmail().equalsIgnoreCase(request.getEmail())) {
            validateEmailUnico(request.getEmail());
        }

        usuarioMapper.updateEntity(request, usuario);
        
        if (!usuario.getRol().getNombre().equalsIgnoreCase(request.getRol())) {
            usuario.setRol(rolService.getRolByNombre(request.getRol()));
        }

       if (request.getPassword() != null && !request.getPassword().isBlank()) {
            String nuevoHash = passwordEncoder.encode(request.getPassword());
            usuario.setPasswordHash(nuevoHash);
        }

        // Delegamos la actualización del perfil
        perfilService.actualizarPerfil(request.getPerfil(), usuario.getPerfil());

        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void deleteById(Long id) {
        Usuario usuario = getUsuarioById(id);
        if (usuario != null) {
            usuarioRepository.delete(usuario);
        }
    }

    private Usuario getUsuarioById(Long id) {
        Long idUsuario = Objects.requireNonNull(id, "El ID del usuario no puede ser nulo");
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuarios", "ID", id));  
    }

    private void validateEmailUnico(String email) {
        usuarioRepository.findByEmail(email).ifPresent(u -> {
            throw new DuplicateResourceException("Un Usuario", "email", email, "Vinculado al RUT " + u.getPerfil().getRut());
        });
    }
}