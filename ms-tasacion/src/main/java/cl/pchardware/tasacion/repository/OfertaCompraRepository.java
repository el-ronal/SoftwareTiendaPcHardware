package cl.pchardware.tasacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.tasacion.model.OfertaCompra;

@Repository
public interface OfertaCompraRepository extends JpaRepository<OfertaCompra, Integer> {
    
    boolean existsByEvaluacionTecnica_IdEvaluacion(Integer idEvaluacion);
    
    Optional<OfertaCompra> findByEvaluacionTecnica_IdEvaluacion(Integer idEvaluacion);
}