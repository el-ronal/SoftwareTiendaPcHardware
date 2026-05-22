package cl.pchardware.pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FeignClient hacia ms-stock.
 * Permite verificar disponibilidad de inventario antes de confirmar un pedido.
 */
@FeignClient(name = "ms-stock")
public interface StockClient {

    /**
     * Devuelve todos los registros de inventario para el SKU en todas las bodegas.
     * El service filtra por bodegas NUEVOS/USADOS y suma cantidades.
     */
    @GetMapping("/api/v1/inventarios/sku/{sku}")
    Object findBySku(@PathVariable String sku);
}
