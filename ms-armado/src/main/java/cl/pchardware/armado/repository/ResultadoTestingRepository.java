package cl.pchardware.armado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.armado.model.ResultadoTesting;

@Repository
public interface ResultadoTestingRepository extends JpaRepository<ResultadoTesting, Integer> {
    
    // Verifica si ya existe un resultado de testing para una orden específica
    boolean existsByOrden_IdOrden(Integer idOrden);
    
    // Busca un resultado por el ID de la orden (útil para validaciones al actualizar)
    Optional<ResultadoTesting> findByOrden_IdOrden(Integer idOrden);
}