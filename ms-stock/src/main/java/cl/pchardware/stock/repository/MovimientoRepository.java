// MovimientoRepository.java
package cl.pchardware.stock.repository;

import cl.pchardware.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByInventarioIdInventarioOrderByFechaRegistroDesc(Long idInventario);
}