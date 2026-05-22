// InventarioRepository.java
package cl.pchardware.stock.repository;

import cl.pchardware.stock.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findBySkuProducto(String skuProducto);
    Optional<Inventario> findByBodegaIdBodegaAndSkuProducto(Long idBodega, String skuProducto);
    boolean existsByBodegaCodigoAndSkuProducto(String codigoBodega, String skuProducto); //comentar en un futuro para validar antes de insertar
}