package cl.pchardware.stock.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FeignClient hacia ms-catalogo.
 * Permite que ms-stock valide que un SKU existe en el catálogo antes de
 * crear un registro de inventario.
 */
@FeignClient(name = "ms-catalogo")
public interface CatalogoClient {

    @GetMapping("/api/v1/productos/sku/{sku}")
    Object findBySku(@PathVariable String sku);
}
