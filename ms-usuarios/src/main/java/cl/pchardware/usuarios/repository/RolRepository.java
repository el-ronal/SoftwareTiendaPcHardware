package cl.pchardware.usuarios.repository;

import cl.pchardware.usuarios.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    
    // Aprovecha la restricción UNIQUE del campo 'nombre'
    Optional<Rol> findByNombre(String nombre);
    
    // Útil para validaciones rápidas antes de insertar
    // boolean existsByNombre(String nombre);
}