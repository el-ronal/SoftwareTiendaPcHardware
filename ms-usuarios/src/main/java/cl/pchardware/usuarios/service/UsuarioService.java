package cl.pchardware.usuarios.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.usuarios.dto.UsuarioRequest;
import cl.pchardware.usuarios.dto.UsuarioResponse;
import cl.pchardware.usuarios.mapper.UsuarioMapper;
import cl.pchardware.usuarios.model.Rol;
import cl.pchardware.usuarios.model.Usuario;
import cl.pchardware.usuarios.repository.RolRepository;
import cl.pchardware.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;

    @Transactional(readOnly = true)
    public List<UsuarioResponse> findAll() {
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    @Transactional(readOnly = true)
    public UsuarioResponse findById(Integer id) {
        return usuarioMapper.toResponse(getUsuarioById(id));
    }

    @Transactional
    public UsuarioResponse create(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Usuario", "email", request.getEmail(), request.getEmail());
        }
        Rol rol = getRolById(request.getIdRol());
        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setRol(rol);
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponse update(Integer id, UsuarioRequest request) {
        Usuario usuario = getUsuarioById(id);
        usuarioRepository.findByEmail(request.getEmail())
                .filter(u -> !u.getIdUsuario().equals(id))
                .ifPresent(u -> { throw new DuplicateResourceException("Usuario", "email", request.getEmail(), request.getEmail()); });
        Rol rol = getRolById(request.getIdRol());
        usuarioMapper.updateEntity(request, usuario);
        usuario.setRol(rol);
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void deleteById(Integer id) {
        Usuario usuario = getUsuarioById(id);
        usuarioRepository.delete(usuario);
    }

    private Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario", "ID", id));
    }

    private Rol getRolById(Integer idRol) {
        return rolRepository.findById(idRol)
                .orElseThrow(() -> new EntityNotFoundException("Rol", "ID", idRol));
    }
}
