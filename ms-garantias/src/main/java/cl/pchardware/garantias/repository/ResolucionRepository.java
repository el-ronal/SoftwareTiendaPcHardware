package cl.pchardware.garantias.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.garantias.model.Resolucion;

@Repository
public interface ResolucionRepository
        extends JpaRepository<Resolucion, Integer> {

    Optional<Resolucion> findByInspeccionTecnica_IdInspeccion(
            Integer idInspeccion
    );
}