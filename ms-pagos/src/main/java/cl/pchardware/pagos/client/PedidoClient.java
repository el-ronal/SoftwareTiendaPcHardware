package cl.pchardware.pagos.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.pchardware.pedidos.dto.PedidoRequest;
import cl.pchardware.pedidos.dto.PedidoResponse;

@FeignClient(name = "ms-pedidos", contextId = "pedidoClient", path = "/api/v1/pedidos")
public interface PedidoClient {
    @GetMapping
    List<PedidoResponse> findAll();

    @GetMapping("/{id}")
    PedidoResponse findById(@PathVariable("id") Integer id);

    @PostMapping
    PedidoResponse create(@RequestBody PedidoRequest request);

    @PutMapping("/{id}")
    PedidoResponse update(@PathVariable("id") Integer id, @RequestBody PedidoRequest request);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable("id") Integer id);
}
