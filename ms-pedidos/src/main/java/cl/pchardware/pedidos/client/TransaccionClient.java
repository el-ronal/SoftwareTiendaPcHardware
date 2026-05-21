package cl.pchardware.pedidos.client;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cl.pchardware.pagos.dto.TransaccionRequest;
import cl.pchardware.pagos.dto.TransaccionResponse;

@FeignClient(name = "ms-pagos", contextId = "transaccionClient", path = "/api/v1/transacciones")
public interface TransaccionClient {
    @GetMapping
    List<TransaccionResponse> findAll();

    @GetMapping("/{id}")
    TransaccionResponse findById(@PathVariable("id") Integer id);

    @PostMapping
    TransaccionResponse create(@RequestBody TransaccionRequest request);

    @PutMapping("/{id}")
    TransaccionResponse update(@PathVariable("id") Integer id, @RequestBody TransaccionRequest request);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable("id") Integer id);
}
