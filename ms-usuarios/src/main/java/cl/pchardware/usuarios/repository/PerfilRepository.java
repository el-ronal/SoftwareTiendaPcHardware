package cl.pchardware.usuarios.repository;

import cl.pchardware.usuarios.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    
    // Aprovecha la restricción UNIQUE y el índice 'idx_perfil_rut'
    Optional<Perfil> findByRut(String rut);
    
    // Útil para validar si un RUT ya está registrado
    // boolean existsByRut(String rut);
}