package cl.pchardware.armado.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.pchardware.armado.dto.UsuarioResponse;

// Modificado: Cliente Feign enlazado al microservicio ms-usuarios
@FeignClient(name = "ms-usuarios")
public interface UsuarioClient {

    // Modificado: Endpoint síncrono para comprobar los datos y el rol del usuario/técnico
    @GetMapping("/api/usuarios/{idUsuario}")
    UsuarioResponse getUsuarioById(@PathVariable("idUsuario") Integer idUsuario);
}
