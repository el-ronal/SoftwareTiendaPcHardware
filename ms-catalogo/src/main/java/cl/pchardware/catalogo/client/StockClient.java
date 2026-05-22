package cl.pchardware.catalogo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FeignClient hacia ms-stock.
 * Permite que ms-catalogo verifique si un SKU tiene stock antes de mostrarlo
 * como disponible en el catálogo.
 */
@FeignClient(name = "ms-stock")
public interface StockClient {

    @GetMapping("/api/v1/inventarios/sku/{sku}")
    Object findBySku(@PathVariable String sku);
}
