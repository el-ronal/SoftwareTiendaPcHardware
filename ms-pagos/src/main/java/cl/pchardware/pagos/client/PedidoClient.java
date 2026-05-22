package cl.pchardware.pagos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FeignClient hacia ms-pedidos.
 * Permite verificar que el pedido existe antes de registrar un pago.
 */
@FeignClient(name = "ms-pedidos")
public interface PedidoClient {

    @GetMapping("/api/v1/pedidos/{id}")
    Object findById(@PathVariable Long id);
}
