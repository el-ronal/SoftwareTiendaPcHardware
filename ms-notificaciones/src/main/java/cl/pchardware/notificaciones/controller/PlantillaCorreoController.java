package cl.pchardware.notificaciones.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.pchardware.notificaciones.dto.PlantillaCorreoRequest;
import cl.pchardware.notificaciones.dto.PlantillaCorreoResponse;
import cl.pchardware.notificaciones.service.PlantillaCorreoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/plantillas-correo")
public class PlantillaCorreoController {

    private final PlantillaCorreoService plantillaService;

    @GetMapping
    public ResponseEntity<List<PlantillaCorreoResponse>> findAll() {
        return ResponseEntity.ok(plantillaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantillaCorreoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(plantillaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PlantillaCorreoResponse> create(@Valid @RequestBody PlantillaCorreoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plantillaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantillaCorreoResponse> update(@PathVariable Integer id, @Valid @RequestBody PlantillaCorreoRequest request) {
        return ResponseEntity.ok(plantillaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        plantillaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
