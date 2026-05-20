package cl.pchardware.usuarios.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.pchardware.usuarios.dto.RolResponse;
import cl.pchardware.usuarios.mapper.RolMapper;
import cl.pchardware.usuarios.service.RolService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RolController {

    private final RolService rolService;
    private final RolMapper rolMapper;

    @GetMapping
    public ResponseEntity<List<RolResponse>> findAll() {
        // Obtenemos las entidades del Service y las mapeamos a Response aquí en el Controller
        return ResponseEntity.ok(rolMapper.toResponseList(rolService.findAll()));
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<RolResponse> findByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(rolMapper.toResponse(rolService.getRolByNombre(nombre)));
    }
}