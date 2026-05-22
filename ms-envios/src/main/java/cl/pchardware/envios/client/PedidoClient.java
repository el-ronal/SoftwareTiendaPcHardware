package cl.pchardware.envios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FeignClient hacia ms-pedidos.
 * Valida que el pedido exista y esté en estado PAGADO antes de crear un despacho.
 */
@FeignClient(name = "ms-pedidos")
public interface PedidoClient {

    @GetMapping("/api/v1/pedidos/{id}")
    Object findById(@PathVariable Long id);
}
