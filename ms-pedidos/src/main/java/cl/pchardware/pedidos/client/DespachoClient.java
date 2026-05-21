package cl.pchardware.pedidos.client;

import cl.pchardware.envios.dto.DespachoRequest;
import cl.pchardware.envios.dto.DespachoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "ms-envios", contextId = "despachoClient", path = "/api/v1/despachos")
public interface DespachoClient {
    @GetMapping
    List<DespachoResponse> findAll();

    @GetMapping("/{id}")
    DespachoResponse findById(@PathVariable("id") Integer id);

    @GetMapping("/seguimiento/{codigoSeguimiento}")
    DespachoResponse findByCodigoSeguimiento(@PathVariable("codigoSeguimiento") String codigoSeguimiento);

    @PostMapping
    DespachoResponse create(@RequestBody DespachoRequest request);

    @PutMapping("/{id}")
    DespachoResponse update(@PathVariable("id") Integer id, @RequestBody DespachoRequest request);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable("id") Integer id);
}
