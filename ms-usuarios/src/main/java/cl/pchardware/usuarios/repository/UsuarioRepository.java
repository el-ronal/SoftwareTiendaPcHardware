package cl.pchardware.usuarios.repository;

import cl.pchardware.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Aprovecha la restricción UNIQUE del campo 'email'
    Optional<Usuario> findByEmail(String email);
    
    // Útil para validaciones rápidas al registrar un usuario
    // boolean existsByEmail(String email);
    
    // Aprovecha el índice 'idx_usuario_estado' que creaste en el script SQL
    // List<Usuario> findByEstado(String estado);
}