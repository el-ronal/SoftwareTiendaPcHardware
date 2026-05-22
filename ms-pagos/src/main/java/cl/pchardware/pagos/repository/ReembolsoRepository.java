package cl.pchardware.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.pagos.model.Reembolso;

@Repository
public interface ReembolsoRepository extends JpaRepository<Reembolso, Integer> {
}
