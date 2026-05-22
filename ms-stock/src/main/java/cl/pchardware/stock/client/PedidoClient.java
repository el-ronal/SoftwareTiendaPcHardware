package cl.pchardware.stock.client;

import cl.pchardware.stock.dto.DetallePedido;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-pedidos")
public interface PedidoClient {
    
    @GetMapping("/api/detalles/pedido/{idPedido}") // Cambia esta URL por la real de tu Controller en ms-pedidos
    List<DetallePedido> getDetallesByPedidoId(@PathVariable("idPedido") Integer idPedido);
}