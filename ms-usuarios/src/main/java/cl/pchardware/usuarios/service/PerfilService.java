package cl.pchardware.usuarios.service;

import org.springframework.stereotype.Service;

import cl.pchardware.usuarios.dto.PerfilRequest;
import cl.pchardware.usuarios.mapper.PerfilMapper;
import cl.pchardware.usuarios.model.Perfil;
import cl.pchardware.usuarios.model.Usuario;
import cl.pchardware.usuarios.repository.PerfilRepository;
import cl.pchardware.common.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final PerfilMapper perfilMapper;

    /**
     * Construye un Perfil validado y lo asocia al Usuario.
     */
    public Perfil buildPerfilParaUsuario(PerfilRequest request, Usuario usuario) {
        validateRutUnico(request.getRut());
        
        Perfil perfil = perfilMapper.toEntity(request);
        perfil.setUsuario(usuario); // Enlace bidireccional
        
        return perfil;
    }

    /**
     * Actualiza el perfil existente, validando si el RUT cambió.
     */
    public void actualizarPerfil(PerfilRequest request, Perfil perfilActual) {
        if (!perfilActual.getRut().equalsIgnoreCase(request.getRut())) {
            validateRutUnico(request.getRut());
        }
        perfilMapper.updateEntity(request, perfilActual);
    }

    private void validateRutUnico(String rut) {
        perfilRepository.findByRut(rut).ifPresent(p -> { 
            throw new DuplicateResourceException("Un Perfil", "RUT", rut, p.getNombreCompleto()); 
        });
    }
}