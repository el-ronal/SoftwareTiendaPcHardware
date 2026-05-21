package cl.pchardware.pedidos.client;

import cl.pchardware.envios.dto.CourierRequest;
import cl.pchardware.envios.dto.CourierResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "ms-envios", contextId = "courierClient", path = "/api/v1/couriers")
public interface CourierClient {
    @GetMapping
    List<CourierResponse> findAll();

    @GetMapping("/{id}")
    CourierResponse findById(@PathVariable("id") Integer id);

    @GetMapping("/codigo/{codigo}")
    CourierResponse findByCodigo(@PathVariable("codigo") String codigo);

    @PostMapping
    CourierResponse create(@RequestBody CourierRequest request);

    @PutMapping("/{id}")
    CourierResponse update(@PathVariable("id") Integer id, @RequestBody CourierRequest request);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable("id") Integer id);
}
