package cl.pchardware.garantias.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.garantias.model.InspeccionTecnica;

@Repository
public interface InspeccionTecnicaRepository
        extends JpaRepository<InspeccionTecnica, Integer> {

    Optional<InspeccionTecnica> findByTicketGarantia_IdTicket(
            Integer idTicket
    );
}