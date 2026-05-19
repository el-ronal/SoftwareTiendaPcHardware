package cl.pchardware.stock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.pchardware.stock.model.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    List<Inventario> findBySkuProducto(String skuProducto);

    List<Inventario> findByBodega_IdBodega(Integer idBodega);

    Optional<Inventario> findByBodega_IdBodegaAndSkuProducto(Integer idBodega, String skuProducto);
}
