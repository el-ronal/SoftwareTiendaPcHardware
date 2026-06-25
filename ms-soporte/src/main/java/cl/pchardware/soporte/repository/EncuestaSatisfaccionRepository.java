package cl.pchardware.soporte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.soporte.model.EncuestaSatisfaccion;

@Repository
public interface EncuestaSatisfaccionRepository
        extends JpaRepository<EncuestaSatisfaccion, Integer> {

    Optional<EncuestaSatisfaccion> findByTicketSoporte_IdTicket(
            Integer idTicket
    );
}