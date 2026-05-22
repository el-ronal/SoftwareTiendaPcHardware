package cl.pchardware.envios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.envios.model.Courier;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Integer> {
    Optional<Courier> findByCodigo(String codigo);
}
