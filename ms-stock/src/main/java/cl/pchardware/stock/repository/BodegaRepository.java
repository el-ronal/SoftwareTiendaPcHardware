package cl.pchardware.stock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.stock.model.Bodega;
import cl.pchardware.stock.model.TipoBodega;

import java.util.List;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Integer> {

    Optional<Bodega> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<Bodega> findByTipo(TipoBodega tipo);
}
