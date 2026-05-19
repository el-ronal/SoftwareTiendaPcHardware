package cl.pchardware.notificaciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.notificaciones.model.PlantillaCorreo;

@Repository
public interface PlantillaCorreoRepository extends JpaRepository<PlantillaCorreo, Integer> {

    Optional<PlantillaCorreo> findByCodigoEvento(String codigoEvento);

    boolean existsByCodigoEvento(String codigoEvento);
}
