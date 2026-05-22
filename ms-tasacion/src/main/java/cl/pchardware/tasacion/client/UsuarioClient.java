package cl.pchardware.tasacion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.pchardware.tasacion.dto.UsuarioClientResponse;

@FeignClient(name = "ms-usuarios")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioClientResponse getUsuarioById(@PathVariable("id") Long id);
}
