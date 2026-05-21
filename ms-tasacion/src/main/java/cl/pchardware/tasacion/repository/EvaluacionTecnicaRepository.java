package cl.pchardware.tasacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.tasacion.model.EvaluacionTecnica;

@Repository
public interface EvaluacionTecnicaRepository extends JpaRepository<EvaluacionTecnica, Integer> {
    
    boolean existsBySolicitudTasacion_IdSolicitud(Integer idSolicitud);
    
    Optional<EvaluacionTecnica> findBySolicitudTasacion_IdSolicitud(Integer idSolicitud);
}