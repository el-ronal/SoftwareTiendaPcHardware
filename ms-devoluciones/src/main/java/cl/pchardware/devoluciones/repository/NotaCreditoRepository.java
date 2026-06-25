package cl.pchardware.devoluciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.devoluciones.model.NotaCredito;

@Repository
public interface NotaCreditoRepository
        extends JpaRepository<NotaCredito, Integer> {

    Optional<NotaCredito> findByRecepcionLogistica_IdRecepcion(
            Integer idRecepcion
    );
}