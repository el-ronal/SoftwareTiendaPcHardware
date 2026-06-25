package cl.pchardware.devoluciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.devoluciones.model.RecepcionLogistica;

@Repository
public interface RecepcionLogisticaRepository
        extends JpaRepository<RecepcionLogistica, Integer> {

    Optional<RecepcionLogistica> findBySolicitudDevolucion_IdDevolucion(
            Integer idDevolucion
    );
}