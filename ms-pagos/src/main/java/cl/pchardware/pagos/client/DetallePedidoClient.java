package cl.pchardware.pagos.client;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.pchardware.pedidos.dto.DetallePedidoRequest;
import cl.pchardware.pedidos.dto.DetallePedidoResponse;

@FeignClient(name = "ms-pedidos", contextId = "detallePedidoClient", path = "/api/v1/detalles-pedido")
public interface DetallePedidoClient {
    @GetMapping
    List<DetallePedidoResponse> findAll();

    @GetMapping("/{id}")
    DetallePedidoResponse findById(@PathVariable("id") Integer id);

    @PostMapping
    DetallePedidoResponse create(@RequestBody DetallePedidoRequest request);

    @PutMapping("/{id}")
    DetallePedidoResponse update(@PathVariable("id") Integer id, @RequestBody DetallePedidoRequest request);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable("id") Integer id);
}
