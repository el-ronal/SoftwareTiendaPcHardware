package cl.pchardware.usuarios.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pchardware.common.exception.DuplicateResourceException;
import cl.pchardware.common.exception.EntityNotFoundException;
import cl.pchardware.common.exception.ReferentialIntegrityException;
import cl.pchardware.usuarios.dto.RolRequest;
import cl.pchardware.usuarios.dto.RolResponse;
import cl.pchardware.usuarios.mapper.RolMapper;
import cl.pchardware.usuarios.model.Rol;
import cl.pchardware.usuarios.repository.RolRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    @Transactional(readOnly = true)
    public List<RolResponse> findAll() {
        return rolMapper.toResponseList(rolRepository.findAll());
    }

    @Transactional(readOnly = true)
    public RolResponse findById(Integer id) {
        return rolMapper.toResponse(getRolById(id));
    }

    @Transactional
    public RolResponse create(RolRequest request) {
        if (rolRepository.existsByNombre(request.getNombre())) {
            throw new DuplicateResourceException("Rol", "nombre", request.getNombre(), request.getNombre());
        }
        Rol rol = rolMapper.toEntity(request);
        return rolMapper.toResponse(rolRepository.save(rol));
    }

    @Transactional
    public RolResponse update(Integer id, RolRequest request) {
        Rol rol = getRolById(id);
        rolRepository.findByNombre(request.getNombre())
                .filter(r -> !r.getIdRol().equals(id))
                .ifPresent(r -> { throw new DuplicateResourceException("Rol", "nombre", request.getNombre(), request.getNombre()); });
        rolMapper.updateEntity(request, rol);
        return rolMapper.toResponse(rolRepository.save(rol));
    }

    @Transactional
    public void deleteById(Integer id) {
        Rol rol = getRolById(id);
        if (rol.getUsuarios() != null && !rol.getUsuarios().isEmpty()) {
            throw new ReferentialIntegrityException("Rol", id, "Usuarios");
        }
        rolRepository.delete(rol);
    }

    private Rol getRolById(Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol", "ID", id));
    }
}
