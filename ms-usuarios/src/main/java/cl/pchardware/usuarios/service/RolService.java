package cl.pchardware.usuarios.service;

import java.util.List;
import org.springframework.stereotype.Service;

import cl.pchardware.usuarios.model.Rol;
import cl.pchardware.usuarios.repository.RolRepository;
import cl.pchardware.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public Rol getRolByNombre(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new EntityNotFoundException("Roles", "nombre", nombre));
    }
}