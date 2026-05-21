package cl.pchardware.pedidos.client;

import cl.pchardware.envios.dto.DireccionEnvioRequest;
import cl.pchardware.envios.dto.DireccionEnvioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "ms-envios", contextId = "direccionEnvioClient", path = "/api/v1/direcciones-envio")
public interface DireccionEnvioClient {
    @GetMapping
    List<DireccionEnvioResponse> findAll();

    @GetMapping("/{id}")
    DireccionEnvioResponse findById(@PathVariable("id") Integer id);

    @GetMapping("/pedido/{idPedido}")
    DireccionEnvioResponse findByIdPedido(@PathVariable("idPedido") Integer idPedido);

    @PostMapping
    DireccionEnvioResponse create(@RequestBody DireccionEnvioRequest request);

    @PutMapping("/{id}")
    DireccionEnvioResponse update(@PathVariable("id") Integer id, @RequestBody DireccionEnvioRequest request);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable("id") Integer id);
}
