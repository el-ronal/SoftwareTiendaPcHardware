package cl.pchardware.envios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.envios.model.DireccionEnvio;

@Repository
public interface DireccionEnvioRepository extends JpaRepository<DireccionEnvio, Integer> {
    Optional<DireccionEnvio> findByIdPedido(Integer idPedido);
}
