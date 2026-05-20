package cl.pchardware.usuarios.service;

import java.util.List;
import org.springframework.stereotype.Service;

import cl.pchardware.usuarios.dto.UsuarioRequest;
import cl.pchardware.usuarios.dto.UsuarioResponse;
import cl.pchardware.usuarios.mapper.UsuarioMapper;
import cl.pchardware.usuarios.model.Perfil;
import cl.pchardware.usuarios.model.Usuario;
import cl.pchardware.usuarios.repository.UsuarioRepository;
import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    
    // Inyectamos los servicios hermanos
    private final RolService rolService;
    private final PerfilService perfilService;

    public List<UsuarioResponse> findAll() {
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    public UsuarioResponse findById(long id) {
        return usuarioMapper.toResponse(getUsuarioById(id));
    }

    @Transactional
    public UsuarioResponse create(UsuarioRequest request) {
        validateEmailUnico(request.getEmail());

        Usuario usuario = usuarioMapper.toEntity(request);
        
        // 1. Delegamos la obtención del Rol
        usuario.setRol(rolService.getRolByNombre(request.getRol()));
        
        // 2. Asignamos la contraseña directamente (como en el ejemplo del profesor)
        usuario.setPasswordHash(request.getPassword());

        // 3. Delegamos la construcción del Perfil
        Perfil perfil = perfilService.buildPerfilParaUsuario(request.getPerfil(), usuario);
        usuario.setPerfil(perfil);

        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponse update(long id, UsuarioRequest request) {
        Usuario usuario = getUsuarioById(id);

        if (!usuario.getEmail().equalsIgnoreCase(request.getEmail())) {
            validateEmailUnico(request.getEmail());
        }

        usuarioMapper.updateEntity(request, usuario);
        
        if (!usuario.getRol().getNombre().equalsIgnoreCase(request.getRol())) {
            usuario.setRol(rolService.getRolByNombre(request.getRol()));
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            usuario.setPasswordHash(request.getPassword());
        }

        // Delegamos la actualización del perfil
        perfilService.actualizarPerfil(request.getPerfil(), usuario.getPerfil());

        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void deleteById(long id) {
        Usuario usuario = getUsuarioById(id);
        usuarioRepository.delete(usuario);
    }

    private Usuario getUsuarioById(long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuarios", "ID", id));  
    }

    private void validateEmailUnico(String email) {
        usuarioRepository.findByEmail(email).ifPresent(u -> {
            throw new DuplicateResourceException("Un Usuario", "email", email, "Vinculado al RUT " + u.getPerfil().getRut());
        });
    }
}