package cl.pchardware.pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FeignClient hacia ms-catalogo.
 * Se usa en PedidoService para validar que el SKU exista antes de crear un detalle.
 */
@FeignClient(name = "ms-catalogo")
public interface CatalogoClient {

    @GetMapping("/api/v1/productos/sku/{sku}")
    Object findBySku(@PathVariable String sku);

    // Endpoint de existencia simple: devuelve 200 si existe, 404 si no
    @GetMapping("/api/v1/productos/sku/{sku}")
    boolean existsBySku(@PathVariable String sku);
}
