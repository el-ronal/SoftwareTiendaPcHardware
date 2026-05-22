package cl.pchardware.envios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.envios.model.Despacho;

@Repository
public interface DespachoRepository extends JpaRepository<Despacho, Integer> {
    Optional<Despacho> findByCodigoSeguimiento(String codigoSeguimiento);
}
