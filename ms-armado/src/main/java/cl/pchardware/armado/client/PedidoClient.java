package cl.pchardware.armado.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.pchardware.armado.dto.PedidoResponse;

@FeignClient(name = "ms-pedidos")
public interface PedidoClient {
    
    @GetMapping("/api/v1/pedidos/{id}")
    PedidoResponse getPedidoById(@PathVariable Integer id);
}
